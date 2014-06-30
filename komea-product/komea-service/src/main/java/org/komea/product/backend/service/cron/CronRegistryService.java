
package org.komea.product.backend.service.cron;



import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;

import org.komea.eventory.api.cache.BackupDelay;
import org.komea.product.backend.api.exceptions.CronRuntimeException;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;



/**
 */
@Service
public class CronRegistryService implements ICronRegistryService, ApplicationContextAware
{


    private static Logger    LOGGER = LoggerFactory.getLogger(CronRegistryService.class);

    private SchedulerFactory schedulerFactory;



    public CronRegistryService() {


        super();
    }


    @PreDestroy
    public void destroy() {


        try {

            schedulerFactory.getScheduler().clear();
            schedulerFactory.getScheduler().shutdown(false); // Kill without wait
        } catch (final SchedulerException e) {
            throw new CronRuntimeException(e);
        }
    }


    /**
     * Method disableCronTask.
     *
     * @param _cronName
     *            String
     * @see org.komea.product.backend.service.cron.ICronRegistryService#disableCronTask(String)
     */
    @Override
    public void disableCronTask(final String _cronName) {


        LOGGER.info("Disabling the cron task {}", _cronName);
        try {
            schedulerFactory.getScheduler().pauseTrigger(TriggerKey.triggerKey(_cronName));
        } catch (final SchedulerException e) {
            throw new CronRuntimeException(e);
        }

    }


    /**
     * Method enableCronTask.
     *
     * @param _cronName
     *            String
     * @see org.komea.product.backend.service.cron.ICronRegistryService#enableCronTask(String)
     */
    @Override
    public void enableCronTask(final String _cronName) {


        LOGGER.info("Enabling the cron task {}", _cronName);
        try {
            schedulerFactory.getScheduler().resumeTrigger(TriggerKey.triggerKey(_cronName));
        } catch (final SchedulerException e) {
            throw new CronRuntimeException(e);
        }

    }


    /**
     * Method existCron.
     *
     * @param _jobName
     *            String
     * @return boolean
     * @see org.komea.product.backend.service.cron.ICronRegistryService#existCron(String)
     */
    @Override
    public boolean existCron(final String _jobName) {


        try {
            return schedulerFactory.getScheduler().checkExists(JobKey.jobKey(_jobName));
        } catch (final SchedulerException e) {
            throw new CronRuntimeException(e);
        }
    }


    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.service.cron.ICronRegistryService#forceNow(
     * java.lang.String)
     */
    @Override
    public void forceNow(final String _jobName) {


        try {
            schedulerFactory.getScheduler().triggerJob(JobKey.jobKey(_jobName));
        } catch (final SchedulerException e) {
            throw new CronRuntimeException(e);
        }

    }


    /**
     * Method getCronTasks.
     *
     * @return List<CronDetails>
     * @see org.komea.product.backend.service.cron.ICronRegistryService#getCronTasks()
     */
    @Override
    public List<CronDetails> getCronTasks() {


        final List<CronDetails> cronDetailsList = new ArrayList<CronDetails>();
        try {
            for (final JobKey jobKey : schedulerFactory.getScheduler().getJobKeys(
                    GroupMatcher.anyJobGroup())) {
                schedulerFactory.getScheduler().getJobDetail(jobKey);
                final CronDetails cronDetails = new CronDetails();
                cronDetails.setCronName(jobKey.getName());

                final TriggerKey triggerKey = TriggerKey.triggerKey(jobKey.getName());
                final Trigger trigger = schedulerFactory.getScheduler().getTrigger(triggerKey);
                if (trigger instanceof CronTrigger) {
                    cronDetails.setCronExpression(((CronTrigger) trigger).getCronExpression());
                }
                cronDetails.setLastTime(trigger.getPreviousFireTime());
                cronDetails.setNextTime(trigger.getNextFireTime());
                
                final TriggerState triggerState =
                        schedulerFactory.getScheduler().getTriggerState(triggerKey);
                
                cronDetails.setStatus(triggerState);
                cronDetailsList.add(cronDetails);
            }
        } catch (final SchedulerException e) {
            throw new CronRuntimeException(e);
        }

        return cronDetailsList;
    }


    public SchedulerFactory getSchedulerFactory() {


        return schedulerFactory;
    }


    @Override
    public void registerCronTask(
            final String _cronName,
            final BackupDelay _backupDelay,
            final Class<? extends Job> _runnable,
            final JobDataMap _properties) {


        CronScheduleBuilder cronSchedule;

        switch (_backupDelay) {
            case DAY:
                cronSchedule = CronScheduleBuilder.dailyAtHourAndMinute(20, 0);
                break;
            case HOUR:
                cronSchedule = CronScheduleBuilder.cronSchedule("0 0/60 * * * ?");
                break;
            case MONTH:
                cronSchedule = CronScheduleBuilder.monthlyOnDayAndHourAndMinute(1, 10, 0);
                break;
            case WEEK:
                cronSchedule = CronScheduleBuilder.weeklyOnDayAndHourAndMinute(7, 12, 0);
                break;
            default:
                throw new UnsupportedOperationException("Enum is unknown : " + _backupDelay);
        }
        createNewCronJob(_cronName, _runnable, _properties, cronSchedule);

    }


    // For more informations :
    // http://quartz-scheduler.org/documentation/quartz-2.x/tutorials/tutorial-lesson-02
    /**
     * Method registerCronTask.
     *
     * @param _cronName
     *            String
     * @param _cronExpression
     *            String
     * @param _runnable
     *            Class<? extends Job>
     * @param _properties
     *            JobDataMap
     * @see org.komea.product.backend.service.cron.ICronRegistryService#
     *      registerCronTask(String, String, Class<? extends Job>, JobDataMap)
     */
    @Override
    public void registerCronTask(
            final String _cronName,
            final String _cronExpression,
            final Class<? extends Job> _runnable,
            final JobDataMap _properties) {


        final CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(_cronExpression);
        createNewCronJob(_cronName, _runnable, _properties, cronSchedule);

    }


    /**
     * Method removeCronTask.
     *
     * @param _cronName
     *            String
     * @see org.komea.product.backend.service.cron.ICronRegistryService#removeCronTask(String)
     */
    @Override
    public void removeCronTask(final String _cronName) {


        try {
            schedulerFactory.getScheduler().deleteJob(JobKey.jobKey(_cronName));
        } catch (final SchedulerException e) {
            throw new CronRuntimeException(e);
        }

    }


    /*
     * (non-Javadoc)
     * @see
     * org.springframework.context.ApplicationContextAware#setApplicationContext
     * (org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(final ApplicationContext _applicationContext)
            throws BeansException {


        try {

            schedulerFactory = new StdSchedulerFactory();
            schedulerFactory.getScheduler().setJobFactory(
                    new JobInjectionFactory(_applicationContext));
            schedulerFactory.getScheduler().start();

        } catch (final SchedulerException e) {
            throw new CronRuntimeException(e);
        }

    }


    public void setSchedulerFactory(final SchedulerFactory _schedulerFactory) {


        schedulerFactory = _schedulerFactory;
    }


    /**
     * Method updateCronFrequency.
     *
     * @param _cronName
     *            String
     * @param _cronExpression
     *            String
     * @see org.komea.product.backend.service.cron.ICronRegistryService#updateCronFrequency(String, String)
     */
    @Override
    public void updateCronFrequency(final String _cronName, final String _cronExpression) {


        try {
            final JobDetail jobDetail =
                    schedulerFactory.getScheduler().getJobDetail(JobKey.jobKey(_cronName));

            final JobDataMap jobDataMap = jobDetail.getJobDataMap();

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


    private void createNewCronJob(
            final String _cronName,
            final Class<? extends Job> _runnable,
            final JobDataMap _properties,
            final CronScheduleBuilder cronSchedule) throws CronRuntimeException {


        try {

            LOGGER.info("Registering new CRON {}", _cronName);
            final JobDetail jobDetail =
                    JobBuilder.newJob(_runnable).withIdentity(_cronName).withDescription(_cronName)
                    .usingJobData(_properties).build();

            final Trigger trigger =
                    TriggerBuilder.newTrigger().forJob(jobDetail).usingJobData(_properties)
                    .withIdentity(_cronName).withSchedule(cronSchedule).build();
            schedulerFactory.getScheduler().scheduleJob(jobDetail, trigger);
        } catch (final SchedulerException e) {
            try {
                schedulerFactory.getScheduler().deleteJob(JobKey.jobKey(_cronName));
            } catch (final SchedulerException e1) {
                throw new CronRuntimeException("Impossible to create the cron task", e);
            }
            throw new CronRuntimeException("Impossible to create the cron task", e);
        }
    }

}
