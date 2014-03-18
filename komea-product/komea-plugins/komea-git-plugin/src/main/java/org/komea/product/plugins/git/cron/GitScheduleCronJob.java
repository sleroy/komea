
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
    private static final String GIT_CRON_VALUE = "0 0/2 * * * ?";
    
    /**
     * 
     */
    private static final String KEY_CRON       = "cron";
    
    /**
     * 
     */
    private static final String KEY_REPO       = "repo";
    
    /**
     * 
     */
    private static final String KEY_REPOSITORY = "repository";
    
    private static final Logger LOGGER         = LoggerFactory.getLogger("git-scheduler");
    
    
    
    /**
     * Returns the list of required keys.
     * 
     * @return the list of required keys.
     */
    public static String[] requiredKeys() {
    
    
        return new String[]
            { "esperEngine", KEY_REPOSITORY, "gitcloner", "personService", KEY_CRON };
    }
    
    
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
    
    public void checkIfGitRepositoryHaveJobs(
            final IGitRepositoryService repository,
            final ICronRegistryService cronRegistryService,
            final JobDataMap _jobDataMap) {
    
    
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
    
    
        final IGitRepositoryService repository =
                (IGitRepositoryService) _context.getMergedJobDataMap().get(KEY_REPOSITORY);
        final ICronRegistryService cronRegistryService =
                (ICronRegistryService) _context.getMergedJobDataMap().get(KEY_CRON);
        Validate.notNull(repository);
        Validate.notNull(cronRegistryService);
        checkIfGitRepositoryHaveJobs(repository, cronRegistryService,
                _context.getMergedJobDataMap());
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
}
