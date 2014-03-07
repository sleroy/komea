
package org.komea.product.plugins.git.bean;



// https://forums.terracotta.org/forums/posts/list/2768.page

import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.esper.IEventPushService;
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
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger("git-cron-fetch");
    
    
    
    /*
     * (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(final JobExecutionContext _context) throws JobExecutionException {
    
    
        final IEventPushService esperEngine = (IEventPushService) _context.get("esperEngine");
        final IGitRepository repository = (IGitRepository) _context.get("repository");
        final IGitClonerService gitcloner = (IGitClonerService) _context.get("gitcloner");
        final GitRepo fetch = (GitRepo) _context.get("repo");
        final IPersonService personService = (IPersonService) _context.get("personService");
        
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
