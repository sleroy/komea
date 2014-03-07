
package org.komea.product.plugins.git.bean;



// https://forums.terracotta.org/forums/posts/list/2768.page

import java.util.List;

import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.plugins.git.model.GitRepo;
import org.komea.product.plugins.git.repositories.api.IGitRepository;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



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
    private static final String GIT_CRON_VALUE = "0 0/1 * * * ?";
    
    private static final Logger LOGGER         = LoggerFactory.getLogger("git-schedule");
    
    
    
    public GitScheduleCronJob() {
    
    
        super();
    }
    
    
    public void checkIfGitRepositoryHaveJobs(
            final IGitRepository repository,
            final ICronRegistryService cronRegistryService,
            final JobDataMap _jobDataMap) {
    
    
        LOGGER.debug("@@@@@ GIT CRON SCHEDULER @@@@@@@");
        final List<GitRepo> feeds = repository.getAllRepositories();
        for (final GitRepo fetch : feeds) {
            if (!repository.isAssociatedToCron(fetch)) {
                final String cronName = repository.initializeCronName(fetch);
                cronRegistryService.registerCronTask(cronName, GIT_CRON_VALUE, GitCronJob.class,
                        prepareJobMapForCron(_jobDataMap, fetch));
                
                
            }
        }
        
        LOGGER.debug("@@@@@                     @@@@@@@");
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(final JobExecutionContext _context) throws JobExecutionException {
    
    
        final IGitRepository repository = (IGitRepository) _context.get("repository");
        final ICronRegistryService cronRegistryService =
                (ICronRegistryService) _context.get("repo");
        
        checkIfGitRepositoryHaveJobs(repository, cronRegistryService,
                _context.getMergedJobDataMap());
    }
    
    
    /**
     * Prepares job map for cron.
     * 
     * @return the job data map.
     */
    public JobDataMap prepareJobMapForCron(final JobDataMap _parentDataMap, final GitRepo _gitRepo) {
    
    
        final JobDataMap properties = new JobDataMap(_parentDataMap.getWrappedMap());
        properties.put("repo", _gitRepo);
        return properties;
    }
}
