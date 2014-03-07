
package org.komea.product.plugins.git.cron;



// https://forums.terracotta.org/forums/posts/list/2768.page

import org.apache.commons.lang.Validate;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.plugins.git.bean.GitEventFactory;
import org.komea.product.plugins.git.bean.IGitClonerService;
import org.komea.product.plugins.git.model.GitRepo;
import org.komea.product.plugins.git.repositories.api.IGitRepository;
import org.komea.product.plugins.git.utils.GitCloner;
import org.komea.product.plugins.git.utils.GitRepositoryReader;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class GitCronJob implements Job
{
    
    
    /**
     * 
     */
    private static final String KEY_CRON           = "cron";
    /**
     * 
     */
    private static final String KEY_ESPER_ENGINE   = "esperEngine";
    /**
     * 
     */
    private static final String KEY_GITCLONER      = "gitcloner";
    /**
     * 
     */
    private static final String KEY_PERSON_SERVICE = "personService";
    /**
     * 
     */
    private static final String KEY_REPO           = "repo";
    /**
     * 
     */
    private static final String KEY_REPOSITORY     = "repository";
    private static final Logger LOGGER             = LoggerFactory.getLogger("git-cron-fetch");
    
    
    
    /**
     * Returns the list of required keys.
     * 
     * @return the list of required keys.
     */
    public static String[] requiredKeys() {
    
    
        return new String[] {
                KEY_ESPER_ENGINE, KEY_REPOSITORY, KEY_GITCLONER, KEY_PERSON_SERVICE, KEY_CRON };
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(final JobExecutionContext _context) throws JobExecutionException {
    
    
        final IEventPushService esperEngine = (IEventPushService) _context.get(KEY_ESPER_ENGINE);
        final IGitRepository repository = (IGitRepository) _context.get(KEY_REPOSITORY);
        final IGitClonerService gitcloner = (IGitClonerService) _context.get(KEY_GITCLONER);
        final GitRepo fetch = (GitRepo) _context.get(KEY_REPO);
        final IPersonService personService = (IPersonService) _context.get(KEY_PERSON_SERVICE);
        
        executeGitCron(esperEngine, repository, gitcloner, fetch, personService);
        
        
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
            final IGitRepository repository,
            final IGitClonerService gitcloner,
            final GitRepo gitRepositoryDefinition,
            final IPersonService _personService) {
    
    
        Validate.notNull(esperEngine);
        Validate.notNull(repository);
        Validate.notNull(gitcloner);
        Validate.notNull(gitRepositoryDefinition);
        Validate.notNull(_personService);
        repository.saveOrUpdate(gitRepositoryDefinition);
        
        try {
            
            LOGGER.debug("Fetching GitRepo feed  : {} {}", gitRepositoryDefinition.getRepoName(),
                    gitRepositoryDefinition.getUrl());
            final GitCloner gitCloner = gitcloner.getOrCreate(gitRepositoryDefinition);
            
            
            new GitRepositoryReader(gitRepositoryDefinition, esperEngine, gitCloner, _personService)
                    .feed();
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            new GitEventFactory().sendGitFetchFailed(gitRepositoryDefinition);
            
        } finally {
            repository.saveOrUpdate(gitRepositoryDefinition);
        }
    }
}
