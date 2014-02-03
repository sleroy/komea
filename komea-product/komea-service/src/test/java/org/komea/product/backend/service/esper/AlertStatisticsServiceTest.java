/**
 * 
 */

package org.komea.product.backend.service.esper;



import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.database.alert.Alert;
import org.komea.product.database.alert.AlertBuilder;
import org.komea.product.service.dto.AlertTypeStatistic;



/**
 * @author sleroy
 */
public class AlertStatisticsServiceTest
{
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.esper.AlertStatisticsService#getReceivedAlertTypesIn24Hours()}.
     */
    @Test
    public final void testGetReceivedAlertTypesIn24Hours() {
    
    
        final EsperEngineBean esperEngineBean = new EsperEngineBean();
        esperEngineBean.init();
        
        final EsperQueryServiceBean esperQueryService = new EsperQueryServiceBean();
        esperQueryService.setEsperEngineService(esperEngineBean);
        
        
        final AlertStatisticsService alertStatisticsService = new AlertStatisticsService();
        alertStatisticsService.setQueryService(esperQueryService);
        alertStatisticsService.setEsperEngine(esperEngineBean);
        
        alertStatisticsService.init();
        
        final Alert alert =
                AlertBuilder.newAlert().type("TYPE1").provided("JENKINS").category("SCM")
                        .getAlert();
        esperEngineBean.sendAlert(alert);
        esperEngineBean.sendAlert(alert);
        esperEngineBean.sendAlert(alert);
        esperEngineBean.sendAlert(alert);
        
        final List<AlertTypeStatistic> receivedAlertTypesIn24Hours =
                alertStatisticsService.getReceivedAlertTypesIn24Hours();
        final AlertTypeStatistic alertTypeStatistic = receivedAlertTypesIn24Hours.get(0);
        Assert.assertEquals(4L, alertTypeStatistic.getNumber());
        Assert.assertEquals("JENKINS", alertTypeStatistic.getProvider());
        System.out.println(receivedAlertTypesIn24Hours);
        
    }
    
}
