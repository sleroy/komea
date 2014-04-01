
package org.komea.product.plugins.scm.cron;



// https://forums.terracotta.org/forums/posts/list/2768.page

import java.util.List;

import org.apache.commons.lang.Validate;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.repositories.api.IScmRepositoryService;
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
public class ScmScheduleCronJob implements Job
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
     * 
     */
    private static final String SCMCLONER      = "scmcloner";
    
    
    
    /**
     * Returns the list of required keys.
     * 
     * @return the list of required keys.
     */
    public static String[] requiredKeys() {
    
    
        return new String[]
            { "esperEngine", KEY_REPOSITORY, SCMCLONER, "personService", KEY_CRON };
    }
    
    
    /**
     * Check if a repository has an associated cron job.
     * 
     * @param _repository
     *            the repository
     * @param cronRegistryService
     *            the cron registry service.
     * @param _jobDataMap
     *            the job data map.
     */
    
    public void checkIfGitRepositoryHaveJobs(
            final IScmRepositoryService _repository,
            final ICronRegistryService cronRegistryService,
            final JobDataMap _jobDataMap) {
    
    
        LOGGER.info("@@@@@ SCM CRON SCHEDULER @@@@@@@");
        final List<ScmRepositoryDefinition> feeds = _repository.getAllRepositories();
        int processed = 0;
        for (final ScmRepositoryDefinition fetch : feeds) {
            Validate.notNull(fetch);
            if (!_repository.isAssociatedToCron(fetch)) {
                processed++;
                LOGGER.info("Creating Cron for the scm repository {}", fetch.getRepoName());
                final String cronName = _repository.initializeCronName(fetch);
                cronRegistryService.registerCronTask(cronName, GIT_CRON_VALUE, ScmCronJob.class,
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
    
    
        final IScmRepositoryService<ScmRepositoryDefinition> repository =
                (IScmRepositoryService<ScmRepositoryDefinition>) _context.getMergedJobDataMap()
                        .get(KEY_REPOSITORY);
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
            final ScmRepositoryDefinition _gitRepo) {
    
    
        final JobDataMap properties = new JobDataMap(_parentDataMap.getWrappedMap());
        properties.put(KEY_REPO, _gitRepo);
        return properties;
    }
}
