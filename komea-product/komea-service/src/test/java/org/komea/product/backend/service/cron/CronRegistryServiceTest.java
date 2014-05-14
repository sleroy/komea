/**
 * 
 */

package org.komea.product.backend.service.cron;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerKey;
import org.springframework.context.ApplicationContext;

/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class CronRegistryServiceTest {

	@Mock
	private ApplicationContext	applicationContext;

	@InjectMocks
	private CronRegistryService	cronRegistryService;

	@Mock
	private Scheduler	        scheduler;

	@Mock
	private SchedulerFactory	schedulerFactory;

	@Before
	public void before() throws SchedulerException {

		when(schedulerFactory.getScheduler()).thenReturn(scheduler);
	}

	/**
	 * Test method for
	 * {@link org.komea.product.backend.service.cron.CronRegistryService#disableCronTask(java.lang.String)}
	 * .
	 */
	@Test
	public final void testDisableCronTask() throws Exception {

		cronRegistryService.disableCronTask("GNI");
		verify(scheduler).pauseTrigger(TriggerKey.triggerKey("GNI"));
	}

	/**
	 * Test method for
	 * {@link org.komea.product.backend.service.cron.CronRegistryService#enableCronTask(java.lang.String)}
	 * .
	 */
	@Test
	public final void testEnableCronTask() throws Exception {

		cronRegistryService.enableCronTask("GNI");
		verify(scheduler).resumeTrigger(TriggerKey.triggerKey("GNI"));
	}

	/**
	 * Test method for
	 * {@link org.komea.product.backend.service.cron.CronRegistryService#existCron(java.lang.String)}
	 * .
	 */
	@Test
	public final void testExistCron() throws Exception {

		cronRegistryService.existCron("CRON");
		verify(scheduler).checkExists(JobKey.jobKey("CRON"));
	}

	/**
	 * Test method for
	 * {@link org.komea.product.backend.service.cron.CronRegistryService#forceNow(java.lang.String)}
	 * .
	 */
	@Test
	public final void testForceNow() throws Exception {

		cronRegistryService.forceNow("CRON");
		verify(scheduler).triggerJob(JobKey.jobKey("CRON"));
	}

	/**
	 * Test method for
	 * {@link org.komea.product.backend.service.cron.CronRegistryService#getCronTasks()}
	 * .
	 */
	@Test
	public final void testGetCronTasks_empty() throws Exception {

		final List<CronDetails> cronTasks = cronRegistryService.getCronTasks();
		assertTrue(cronTasks.isEmpty());
	}

	/**
	 * Test method for
	 * {@link org.komea.product.backend.service.cron.CronRegistryService#registerCronTask(java.lang.String, java.lang.String, java.lang.Class, org.quartz.JobDataMap)}
	 * .
	 */
	@Test
	public final void testRegisterCronTask() throws Exception {

		cronRegistryService.registerCronTask("CRON", "* * * * * ?", Job.class, new JobDataMap());
		// TODO::verify(scheduler,
		// times(1)).scheduleJob(Mockito.mock(JobDetail.class),
		// mock(Trigger.class));
	}

	/**
	 * Test method for
	 * {@link org.komea.product.backend.service.cron.CronRegistryService#removeCronTask(java.lang.String)}
	 * .
	 */
	@Test
	public final void testRemoveCronTask() throws Exception {

		cronRegistryService.removeCronTask("CRON");
		verify(scheduler).deleteJob(JobKey.jobKey("CRON"));
	}

	/**
	 * Test method for
	 * {@link org.komea.product.backend.service.cron.CronRegistryService#updateCronFrequency(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public final void testUpdateCronFrequency() throws Exception {

		cronRegistryService.forceNow("CRON");
		verify(scheduler).triggerJob(JobKey.jobKey("CRON"));
	}

}
