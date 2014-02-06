
package org.komea.product.backend.service.cron;



import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.komea.product.backend.exceptions.CronRuntimeException;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



@Service
public class CronRegistryService implements ICronRegistryService
{
    
    
    private static final String CRON_EXP = "cronExp";
    
    
    private static Logger       LOGGER   = LoggerFactory.getLogger(CronRegistryService.class);
    
    
    private SchedulerFactory    schedulerFactory;
    
    
    
    public CronRegistryService() {
    
    
        super();
    }
    
    
    @PreDestroy
    public void destroy() {
    
    
        try {
            schedulerFactory.getScheduler().clear();
        } catch (final SchedulerException e) {
            throw new CronRuntimeException(e);
        }
    }
    
    
    @Override
    public void disableCronTask(final String _cronName) {
    
    
        LOGGER.info("Disabling the cron task {}", _cronName);
        try {
            schedulerFactory.getScheduler().pauseTrigger(TriggerKey.triggerKey(_cronName));
        } catch (final SchedulerException e) {
            throw new CronRuntimeException(e);
        }
        
        
    }
    
    
    @Override
    public void enableCronTask(final String _cronName) {
    
    
        LOGGER.info("Enabling the cron task {}", _cronName);
        try {
            schedulerFactory.getScheduler().resumeTrigger(TriggerKey.triggerKey(_cronName));
        } catch (final SchedulerException e) {
            throw new CronRuntimeException(e);
        }
        
    }
    
    
    @Override
    public boolean existCron(final String _jobName) {
    
    
        try {
            return schedulerFactory.getScheduler().checkExists(JobKey.jobKey(_jobName));
        } catch (final SchedulerException e) {
            throw new CronRuntimeException(e);
        }
    }
    
    
    @Override
    public List<CronDetails> getCronTasks() {
    
    
        final List<CronDetails> cronDetailsList = new ArrayList<CronDetails>();
        try {
            for (final JobKey jobKey : schedulerFactory.getScheduler().getJobKeys(
                    GroupMatcher.anyJobGroup())) {
                final JobDetail jobDetail = schedulerFactory.getScheduler().getJobDetail(jobKey);
                final CronDetails cronDetails = new CronDetails();
                cronDetails.setCronName(jobKey.getName());
                cronDetails.setCronExpression(jobDetail.getJobDataMap().getString(CRON_EXP));
                final TriggerKey triggerKey = TriggerKey.triggerKey(jobKey.getName());
                final Trigger trigger = schedulerFactory.getScheduler().getTrigger(triggerKey);
                cronDetails.setNextTime(trigger.getNextFireTime());
                cronDetails.setStatus(schedulerFactory.getScheduler().getTriggerState(triggerKey));
                cronDetailsList.add(cronDetails);
            }
        } catch (final SchedulerException e) {
            throw new CronRuntimeException(e);
        }
        
        return cronDetailsList;
    }
    
    
    @PostConstruct
    public void initSchedulerFactory() {
    
    
        schedulerFactory = new StdSchedulerFactory();
        try {
            schedulerFactory.getScheduler().start();
        } catch (final SchedulerException e) {
            throw new CronRuntimeException(e);
        }
        
    }
    
    
    // For more informations : http://quartz-scheduler.org/documentation/quartz-2.x/tutorials/tutorial-lesson-02
    @Override
    public void registerCronTask(
            final String _cronName,
            final String _cronExpression,
            final Class<? extends Job> _runnable,
            final JobDataMap _properties) {
    
    
        _properties.put(CRON_EXP, _cronExpression);
        final JobDetail jobDetail =
                JobBuilder.newJob(_runnable).withIdentity(_cronName).withDescription(_cronName)
                        .usingJobData(_properties).build();
        final Trigger trigger =
                TriggerBuilder.newTrigger().forJob(jobDetail).usingJobData(_properties)
                        .withIdentity(_cronName)
                        .withSchedule(CronScheduleBuilder.cronSchedule(_cronExpression)).build();
        try {
            schedulerFactory.getScheduler().scheduleJob(jobDetail, trigger);
        } catch (final SchedulerException e) {
            throw new CronRuntimeException("Impossible to create the cron task", e);
        }
        
    }
    
    
    @Override
    public void removeCronTask(final String _cronName) {
    
    
        try {
            schedulerFactory.getScheduler().deleteJob(JobKey.jobKey(_cronName));
        } catch (final SchedulerException e) {
            throw new CronRuntimeException(e);
        }
        
    }
    
    
    @Override
    public void updateCronFrequency(final String _cronName, final String _cronExpression) {
    
    
        try {
            final JobDetail jobDetail =
                    schedulerFactory.getScheduler().getJobDetail(JobKey.jobKey(_cronName));
            
            final JobDataMap jobDataMap = jobDetail.getJobDataMap();
            jobDataMap.put(CRON_EXP, _cronExpression);
            final Trigger trigger =
                    TriggerBuilder.newTrigger().forJob(_cronExpression).usingJobData(jobDataMap)
                            .withIdentity(_cronName).startNow()
                            .withSchedule(CronScheduleBuilder.cronSchedule(_cronExpression))
                            .build();
            schedulerFactory.getScheduler().rescheduleJob(TriggerKey.triggerKey(_cronExpression),
                    trigger);
        } catch (final SchedulerException e) {
            throw new CronRuntimeException("Impossible to update the cron frequency of the task : "
                    + _cronName, e);
        }
        
        
    }
    
}
