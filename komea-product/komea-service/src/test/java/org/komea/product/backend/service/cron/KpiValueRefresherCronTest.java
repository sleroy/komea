/**
 * 
 */

package org.komea.product.backend.service.cron;



import org.junit.Test;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.mock;



/**
 * @author sleroy
 */
public class KpiValueRefresherCronTest extends AbstractSpringIntegrationTestCase
{
    
    
    @Autowired
    private IKPIService    kpiService;
    @Autowired
    private IStatisticsAPI statisticsAPI;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.cron.KpiValueRefresherCron#execute(org.quartz.JobExecutionContext)}.
     */
    @Test
    public final void testExecute() throws Exception {
    
    
        final KpiValueRefresherCron kpiValueRefresherCron = new KpiValueRefresherCron();
        kpiValueRefresherCron.setKpiService(kpiService);
        kpiValueRefresherCron.setStatisticsAPI(statisticsAPI);
        kpiValueRefresherCron.execute(mock(JobExecutionContext.class));
        
    }
    
}
