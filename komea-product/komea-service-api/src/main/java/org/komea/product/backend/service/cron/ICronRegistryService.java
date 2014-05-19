package org.komea.product.backend.service.cron;

import java.util.List;

import org.komea.eventory.api.cache.BackupDelay;
import org.quartz.Job;
import org.quartz.JobDataMap;

/**
 * This interface defines the cron registry.
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public interface ICronRegistryService {

	/**
	 * Disables a cron task.
	 * 
	 * @param _cronName
	 *            the cron name.
	 */
	void disableCronTask(String _cronName);

	/**
	 * Disables a cron task.
	 * 
	 * @param _cronName
	 *            the cron name.
	 */
	void enableCronTask(String _cronName);

	/**
	 * Verify the existing of a job
	 * 
	 * @param _jobName
	 *            the job name
	 * @return true if the job is existing.
	 */
	boolean existCron(String _jobName);

	/**
	 * Force execution of a job now
	 * 
	 * @param _jobName
	 */
	void forceNow(String _jobName);

	/**
	 * Returns the list of cron tasks.
	 * 
	 * @return the list of cron tasks.
	 */
	List<CronDetails> getCronTasks();

	/**
	 * Register a new cron task.
	 * 
	 * @param _cronName
	 *            the cron name
	 * @param _cronExpression
	 *            the cron expression
	 * @param _runnable
	 *            runnable script.
	 * @param _properties
	 *            JobDataMap
	 */
	void registerCronTask(String _cronName, BackupDelay _backupDelay, Class<? extends Job> _runnable,
	        JobDataMap _properties);

	/**
	 * Register a new cron task.
	 * 
	 * @param _cronName
	 *            the cron name
	 * @param _cronExpression
	 *            the cron expression
	 * @param _runnable
	 *            runnable script.
	 * @param _properties
	 *            JobDataMap
	 */
	void registerCronTask(String _cronName, String _cronExpression, Class<? extends Job> _runnable,
	        JobDataMap _properties);

	/**
	 * Remove a cron task.
	 * 
	 * @param _cronName
	 *            the cron name.
	 */
	void removeCronTask(String _cronName);

	/**
	 * Updates the cron frequency.
	 * 
	 * @param _cronName
	 *            the cron name.
	 * @param _cronExpression
	 *            cron expression
	 */
	void updateCronFrequency(String _cronName, String _cronExpression);
}
