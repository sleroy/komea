/**
 *
 */

package org.komea.product.backend.service.cron;



import org.junit.Test;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;



/**
 * @author sleroy
 */
public class KpiHistoryJobTest
{
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.cron.KpiHistoryJob#execute(org.quartz.JobExecutionContext)} .
     * 
     * @throws JobExecutionException
     */
    @Test
    public final void testExecute() throws JobExecutionException {
    
    
        final KpiHistoryJob kpiHistoryJob = new KpiHistoryJob();
        kpiHistoryJob.setDelay(BackupDelay.DAY);
        
        final IStatisticsAPI kpiValueService = Mockito.mock(IStatisticsAPI.class);
        kpiHistoryJob.setStatisticsService(kpiValueService);
        kpiHistoryJob.execute(Mockito.mock(JobExecutionContext.class));
        verify(kpiValueService, times(1)).backupKpiValuesIntoHistory(
                Matchers.any(BackupDelay.class));
        
    }
}
