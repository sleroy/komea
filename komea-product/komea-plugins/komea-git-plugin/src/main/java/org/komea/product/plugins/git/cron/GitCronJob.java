
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
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



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
    
    
    
    @Autowired
    private IEventPushService       esperEngine   = null;
    
    
    private GitRepositoryDefinition fetch         = null;
    
    
    @Autowired
    private IGitClonerService       gitcloner     = null;
    
    
    private IPersonService          personService = null;
    
    
    @Autowired
    private IGitRepositoryService   repository    = null;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(final JobExecutionContext _context) throws JobExecutionException {
    
    
        executeGitCron();
        
        
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
     * @param fetch
     *            the fetch
     * @param _personService
     */
    public void executeGitCron() {
    
    
        Validate.notNull(esperEngine);
        Validate.notNull(repository);
        Validate.notNull(gitcloner);
        Validate.notNull(fetch);
        Validate.notNull(personService);
        repository.saveOrUpdate(fetch);
        
        try {
            
            LOGGER.info("Fetching git repository : {} {}", fetch.getRepoName(), fetch.getUrl());
            final IGitCloner gitCloner = gitcloner.getOrCreate(fetch);
            
            LOGGER.info("Read repository : {} {}", fetch.getRepoName(), fetch.getUrl());
            new GitRepositoryReader(fetch, esperEngine, gitCloner, personService).feed();
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            new GitEventFactory().sendGitFetchFailed(fetch);
            
        } finally {
            repository.saveOrUpdate(fetch);
        }
    }
    
    
    public IEventPushService getEsperEngine() {
    
    
        return esperEngine;
    }
    
    
    public GitRepositoryDefinition getFetch() {
    
    
        return fetch;
    }
    
    
    public IGitClonerService getGitcloner() {
    
    
        return gitcloner;
    }
    
    
    public IPersonService getPersonService() {
    
    
        return personService;
    }
    
    
    public IGitRepositoryService getRepository() {
    
    
        return repository;
    }
    
    
    public void setEsperEngine(final IEventPushService _esperEngine) {
    
    
        esperEngine = _esperEngine;
    }
    
    
    public void setFetch(final GitRepositoryDefinition _fetch) {
    
    
        fetch = _fetch;
    };
    
    
    public void setGitcloner(final IGitClonerService _gitcloner) {
    
    
        gitcloner = _gitcloner;
    }
    
    
    public void setPersonService(final IPersonService _personService) {
    
    
        personService = _personService;
    }
    
    
    public void setRepository(final IGitRepositoryService _repository) {
    
    
        repository = _repository;
    }
    
    
}
