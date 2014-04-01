
package org.komea.product.plugins.git.cron;



// https://forums.terracotta.org/forums/posts/list/2768.page

import java.util.List;

import org.apache.commons.lang.Validate;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.plugins.git.model.GitRepositoryDefinition;
import org.komea.product.plugins.git.repositories.api.IGitRepositoryService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * This class defines a cron that check periodically that all git repositories are linked to a job to fetch any changes.
 * 
 * @author sleroy
 */
public class GitScheduleCronJob implements Job
{
    
    
    /**
     * Cron value for GIT Provider.
     */
    private static final String   GIT_CRON_VALUE      = "0 0/2 * * * ?";
    
    private static final String   KEY_REPO            = "repo";
    
    
    private static final Logger   LOGGER              = LoggerFactory.getLogger("git-scheduler");
    
    
    @Autowired
    private ICronRegistryService  cronRegistryService = null;
    
    
    @Autowired
    private IGitRepositoryService repository          = null;
    
    
    
    /**
     * Check if a repository has an associated cron job.
     * 
     * @param repository
     *            the repository
     * @param cronRegistryService
     *            the cron registry service.
     * @param _jobDataMap
     *            the job data map.
     */
    
    public void checkIfGitRepositoryHaveJobs(final JobDataMap _jobDataMap) {
    
    
        LOGGER.info("@@@@@ GIT CRON SCHEDULER @@@@@@@");
        final List<GitRepositoryDefinition> feeds = repository.getAllRepositories();
        int processed = 0;
        for (final GitRepositoryDefinition fetch : feeds) {
            Validate.notNull(fetch);
            if (!repository.isAssociatedToCron(fetch)) {
                processed++;
                LOGGER.info("Creating Cron for the git repository {}", fetch.getRepoName());
                final String cronName = repository.initializeCronName(fetch);
                cronRegistryService.registerCronTask(cronName, GIT_CRON_VALUE, GitCronJob.class,
                        prepareJobMapForCron(_jobDataMap, fetch));
                
                
            }
        }
        
        LOGGER.info("@@@@@      {}               @@@@@@@", processed);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(final JobExecutionContext _context) throws JobExecutionException {
    
    
        Validate.notNull(repository);
        Validate.notNull(cronRegistryService);
        checkIfGitRepositoryHaveJobs(_context.getMergedJobDataMap());
    }
    
    
    public ICronRegistryService getCronRegistryService() {
    
    
        return cronRegistryService;
    }
    
    
    public IGitRepositoryService getRepository() {
    
    
        return repository;
    }
    
    
    /**
     * Prepares job map for cron.
     * 
     * @return the job data map.
     */
    public JobDataMap prepareJobMapForCron(
            final JobDataMap _parentDataMap,
            final GitRepositoryDefinition _gitRepo) {
    
    
        final JobDataMap properties = new JobDataMap(_parentDataMap.getWrappedMap());
        properties.put(KEY_REPO, _gitRepo);
        return properties;
    }
    
    
    public void setCronRegistryService(final ICronRegistryService _cronRegistryService) {
    
    
        cronRegistryService = _cronRegistryService;
    }
    
    
    public void setRepository(final IGitRepositoryService _repository) {
    
    
        repository = _repository;
    }
}
