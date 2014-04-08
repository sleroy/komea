/**
 * 
 */

package org.komea.product.backend.service.kpi;



import org.komea.product.backend.service.cron.JobInjectionFactory;
import org.mockito.Mockito;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.spi.OperableTrigger;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



/**
 * @author sleroy
 */
public class CronInstantiatorFactory
{
    
    
    public static void testCronInstantiation(final Class _omplJob, final JobDataMap _jobDataMap)
            throws SchedulerException {
    
    
        final Scheduler schedulerMock = Mockito.mock(Scheduler.class);
        final JobDetail jobDetail = mock(JobDetail.class);
        final ApplicationContext appMock = Mockito.mock(ApplicationContext.class);
        final JobInjectionFactory jobInjectionFactory = new JobInjectionFactory(appMock);
        final OperableTrigger operableTriggerMock = mock(OperableTrigger.class);
        final TriggerFiredBundle triggerBundleMock = Mockito.mock(TriggerFiredBundle.class);
        final AutowireCapableBeanFactory autowireFactory = mock(AutowireCapableBeanFactory.class);
        
        when(triggerBundleMock.getJobDetail()).thenReturn(jobDetail);
        when(jobDetail.getJobClass()).thenReturn(_omplJob);
        when(schedulerMock.getContext()).thenReturn(new SchedulerContext());
        when(jobDetail.getJobDataMap()).thenReturn(_jobDataMap);
        when(triggerBundleMock.getTrigger()).thenReturn(operableTriggerMock);
        when(operableTriggerMock.getJobDataMap()).thenReturn(_jobDataMap);
        when(appMock.getAutowireCapableBeanFactory()).thenReturn(autowireFactory);
        jobInjectionFactory.newJob(triggerBundleMock, schedulerMock);
    }
    
    
    /**
     * 
     */
    public CronInstantiatorFactory() {
    
    
        // TODO Auto-generated constructor stub
    }
    
}
