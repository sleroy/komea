/**
 * 
 */

package org.komea.product.backend.service.kpi;



import org.junit.Test;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Project;
import org.mockito.Mockito;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;



/**
 * @author sleroy
 */
public class KpiHistoryJobTest
{
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.kpi.KpiHistoryJob#execute(org.quartz.JobExecutionContext)}.
     * 
     * @throws JobExecutionException
     */
    @Test 
    public final void testExecute() throws JobExecutionException {
    
    
        final KpiHistoryJob kpiHistoryJob = new KpiHistoryJob();
        final JobExecutionContext mock = Mockito.mock(JobExecutionContext.class);
        
        kpiHistoryJob.setEntity(new Project());
        kpiHistoryJob.setKpi(new Kpi());
        
        
        kpiHistoryJob.setKpiService(Mockito.mock(IKPIService.class));
        final JobDetail mock2 = Mockito.mock(JobDetail.class);
        Mockito.when(mock.getJobDetail()).thenReturn(mock2);
        Mockito.when(mock2.getKey()).thenReturn(new JobKey("gni"));
        kpiHistoryJob.execute(mock);
        
        
    }
}
