
package org.komea.product.plugins.git.cron;



// https://forums.terracotta.org/forums/posts/list/2768.page

import org.apache.commons.lang.Validate;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.plugins.git.model.GitRepositoryDefinition;
import org.komea.product.plugins.git.repositories.api.IGitCloner;
import org.komea.product.plugins.git.repositories.api.IGitClonerService;
import org.komea.product.plugins.git.repositories.api.IGitRepositoryService;
import org.komea.product.plugins.git.utils.GitEventFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@DisallowConcurrentExecution
public class GitCronJob implements Job
{
    
    
    private static final String KEY_CRON           = "cron";
    private static final String KEY_ESPER_ENGINE   = "esperEngine";
    private static final String KEY_GITCLONER      = "gitcloner";
    private static final String KEY_PERSON_SERVICE = "personService";
    private static final String KEY_REPO           = "repo";
    private static final String KEY_REPOSITORY     = "repository";
    private static final Logger LOGGER             = LoggerFactory.getLogger("git-cron-fetch");
    
    
    
    /**
     * Returns the list of required keys.
     * 
     * @return the list of required keys.
     */
    public static String[] requiredKeys() {
    
    
        return new String[]
            { KEY_ESPER_ENGINE, KEY_REPOSITORY, KEY_GITCLONER, KEY_PERSON_SERVICE, KEY_CRON };
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(final JobExecutionContext _context) throws JobExecutionException {
    
    
        final JobDataMap mergedJobDataMap = _context.getMergedJobDataMap();
        runCronTaskWithJobDataMap(mergedJobDataMap);
        
        
    }
    
    
    /**
     * Executes the git cron
     * 
     * @param esperEngine
     *            the event push engine.
     * @param repository
     *            the repository
     * @param gitcloner
     *            the git cloner
     * @param gitRepositoryDefinition
     *            the fetch
     * @param _personService
     */
    public void executeGitCron(
            final IEventPushService esperEngine,
            final IGitRepositoryService repository,
            final IGitClonerService gitcloner,
            final GitRepositoryDefinition gitRepositoryDefinition,
            final IPersonService _personService) {
    
    
        Validate.notNull(esperEngine);
        Validate.notNull(repository);
        Validate.notNull(gitcloner);
        Validate.notNull(gitRepositoryDefinition);
        Validate.notNull(_personService);
        repository.saveOrUpdate(gitRepositoryDefinition);
        
        try {
            
            LOGGER.info("Fetching git repository : {} {}", gitRepositoryDefinition.getRepoName(),
                    gitRepositoryDefinition.getUrl());
            final IGitCloner gitCloner = gitcloner.getOrCreate(gitRepositoryDefinition);
            
            LOGGER.info("Read repository : {} {}", gitRepositoryDefinition.getRepoName(),
                    gitRepositoryDefinition.getUrl());
            new GitRepositoryReader(gitRepositoryDefinition, esperEngine, gitCloner, _personService)
                    .feed();
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            new GitEventFactory().sendGitFetchFailed(gitRepositoryDefinition);
            
        } finally {
            repository.saveOrUpdate(gitRepositoryDefinition);
        }
    }
    
    
    private void runCronTaskWithJobDataMap(final JobDataMap mergedJobDataMap) {
    
    
        final IEventPushService esperEngine =
                (IEventPushService) mergedJobDataMap.get(KEY_ESPER_ENGINE);
        final IGitRepositoryService repository =
                (IGitRepositoryService) mergedJobDataMap.get(KEY_REPOSITORY);
        final IGitClonerService gitcloner = (IGitClonerService) mergedJobDataMap.get(KEY_GITCLONER);
        final GitRepositoryDefinition fetch =
                (GitRepositoryDefinition) mergedJobDataMap.get(KEY_REPO);
        final IPersonService personService =
                (IPersonService) mergedJobDataMap.get(KEY_PERSON_SERVICE);
        
        executeGitCron(esperEngine, repository, gitcloner, fetch, personService);
    }
}
