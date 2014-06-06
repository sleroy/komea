
package org.komea.product.plugins.scm.cron;



// https://forums.terracotta.org/forums/posts/list/2768.page

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.api.IScmRepositoryProxyFactories;
import org.komea.product.plugins.scm.api.IScmRepositoryService;
import org.quartz.DisallowConcurrentExecution;
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
@DisallowConcurrentExecution
public class ScmScheduleCronJob implements Job
{
    
    
    /**
     * Cron value for SCM Provider.
     */
    public static final String                 GIT_CRON_VALUE      = "0 0/2 * * * ?";
    
    /**
     * 
     */
    public static final String                 KEY_REPO            = "repo";
    
    private static final Logger                LOGGER              =
                                                                           LoggerFactory
                                                                                   .getLogger("scm-scheduler");
    
    
    @Autowired
    private ICronRegistryService               cronRegistryService = null;
    
    
    @Autowired
    private final IScmRepositoryProxyFactories factories           = null;
    
    @Autowired
    private final IScmRepositoryService        repositories        = null;
    
    
    
    /**
     * Check if a repositories has an associated cron job.
     * 
     * @param _repository
     *            the repositories
     * @param cronRegistryService
     *            the cron registry service.
     * @param _jobDataMap
     *            the job data map.
     */
    
    public void checkIfScmRepositoriesAreAssociatedToAJob(
            final IScmRepositoryService _repository,
            final JobDataMap _jobDataMap) {
    
    
        LOGGER.info("@@@@@ SCM CRON SCHEDULER @@@@@@@");
        final List<ScmRepositoryDefinition> feeds = _repository.getRepositoriesNotAssociated();
        LOGGER.info("@@@@@  Processed repositories {}  @@@@@@@", feeds.size());
        for (final ScmRepositoryDefinition fetch : feeds) {
            Validate.notNull(fetch);
            LOGGER.info("Creating Cron for the scm repository {} of type {}", fetch.getRepoName(),
                    fetch.getType());
            final String cronName = _repository.registerCronJobOfScm(fetch);
            cronRegistryService.registerCronTask(cronName, GIT_CRON_VALUE, ScmCronJob.class,
                    prepareJobMapForCron(_jobDataMap, fetch));
            
            
        }
        
        LOGGER.info("@@@@@                     @@@@@@@");
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(final JobExecutionContext _context) throws JobExecutionException {
    
    
        Validate.notNull(repositories);
        Validate.notNull(cronRegistryService);
        checkIfScmRepositoriesAreAssociatedToAJob(repositories, _context.getMergedJobDataMap());
    }
    
    
    public ICronRegistryService getCronRegistryService() {
    
    
        return cronRegistryService;
    }
    
    
    public IScmRepositoryProxyFactories getFactories() {
    
    
        return factories;
    }
    
    
    public IScmRepositoryService getRepositories() {
    
    
        return repositories;
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
    
    
    public void setCronRegistryService(final ICronRegistryService _cronRegistryService) {
    
    
        cronRegistryService = _cronRegistryService;
    }
    
}
