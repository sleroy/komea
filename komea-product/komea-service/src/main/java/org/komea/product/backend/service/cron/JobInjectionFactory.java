/**
 * 
 */

package org.komea.product.backend.service.cron;



import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;



/**
 * This class defines a way to inject properties to cron jobs of Quartz.
 * 
 * @author sleroy
 */
public class JobInjectionFactory extends SimpleJobFactory
{
    
    
    private static final Logger      LOGGER = LoggerFactory
                                                    .getLogger("quartzjob-injection-factory");
    
    
    private final ApplicationContext applicationContext;
    
    
    
    /**
     * 
     */
    public JobInjectionFactory(final ApplicationContext _applicationContext) {
    
    
        super();
        applicationContext = _applicationContext;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.quartz.spi.JobFactory#newJob(org.quartz.spi.TriggerFiredBundle, org.quartz.Scheduler)
     */
    @Override
    public Job newJob(final TriggerFiredBundle _bundle, final Scheduler _scheduler)
            throws SchedulerException {
    
    
        final Job newJob = super.newJob(_bundle, _scheduler);
        
        LOGGER.debug("Weaving Job data properties to the bean {}", newJob);
        final JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(_scheduler.getContext());
        jobDataMap.putAll(_bundle.getJobDetail().getJobDataMap());
        jobDataMap.putAll(_bundle.getTrigger().getJobDataMap());
        
        jodd.bean.BeanUtil.getBeanUtilBean().populateBean(newJob, jobDataMap);
        applicationContext.getAutowireCapableBeanFactory().autowireBean(newJob);
        return newJob;
    }
}
