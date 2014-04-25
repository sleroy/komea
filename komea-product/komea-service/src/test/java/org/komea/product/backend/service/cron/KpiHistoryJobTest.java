/**
 *
 */

package org.komea.product.backend.service.cron;



import org.junit.Test;
import org.komea.product.backend.service.kpi.IKpiValueService;
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
     * Test method for {@link org.komea.product.backend.service.cron.KpiHistoryJob#execute(org.quartz.JobExecutionContext)}.
     * 
     * @throws JobExecutionException
     */
    @Test
    public final void testExecute() throws JobExecutionException {
    
    
        final KpiHistoryJob kpiHistoryJob = new KpiHistoryJob();
        final JobExecutionContext mock = Mockito.mock(JobExecutionContext.class);
        final IKpiValueService kpiValueService = Mockito.mock(IKpiValueService.class);
        kpiHistoryJob.setKpiValueService(kpiValueService);
        kpiHistoryJob.execute(mock);
        verify(kpiValueService, times(1)).backupKpiValuesIntoHistory();
        
        
    }
}
