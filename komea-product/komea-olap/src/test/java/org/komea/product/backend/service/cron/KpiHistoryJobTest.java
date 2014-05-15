/**
 *
 */

package org.komea.product.backend.service.cron;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.database.enums.BackupDelay;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author sleroy
 */
public class KpiHistoryJobTest {

	/**
	 * Test method for
	 * {@link org.komea.product.backend.service.cron.KpiHistoryJob#execute(org.quartz.JobExecutionContext)}
	 * .
	 * 
	 * @throws JobExecutionException
	 */
	@Test
	public final void testExecute() throws JobExecutionException {

		final KpiHistoryJob kpiHistoryJob = new KpiHistoryJob();
		final JobExecutionContext mock = Mockito.mock(JobExecutionContext.class);
		final JobDataMap value = new JobDataMap();
		value.put("delay", BackupDelay.DAY);
		when(mock.getMergedJobDataMap()).thenReturn(value);
		final IStatisticsAPI kpiValueService = Mockito.mock(IStatisticsAPI.class);
		kpiHistoryJob.setStatisticsService(kpiValueService);
		kpiHistoryJob.execute(mock);
		verify(kpiValueService, times(1)).backupKpiValuesIntoHistory(Matchers.any(BackupDelay.class));

	}
}
