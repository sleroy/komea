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
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author sleroy
 */
public class AlertStatisticsServiceTest extends AbstractSpringIntegrationTestCase
{
    
    
    @Autowired
    private AlertStatisticsService alertStats;
    
    @Autowired
    private EsperEngineBean        esperEngine;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.esper.AlertStatisticsService#getReceivedAlertTypesIn24LastHours()}.
     */
    @Test
    public final void testGetReceivedAlertTypesIn24LastHours() {
    
    
        final Alert alert =
                AlertBuilder.newAlert().type("TYPE1").provided("JENKINS").category("SCM")
                        .getAlert();
        esperEngine.sendAlert(alert);
        esperEngine.sendAlert(alert);
        esperEngine.sendAlert(alert);
        esperEngine.sendAlert(alert);
        
        final List<AlertTypeStatistic> receivedAlertTypesIn24Hours =
                alertStats.getReceivedAlertTypesIn24LastHours();
        final AlertTypeStatistic alertTypeStatistic = receivedAlertTypesIn24Hours.get(0);
        Assert.assertEquals(4L, alertTypeStatistic.getNumber());
        Assert.assertEquals("JENKINS", alertTypeStatistic.getProvider());
        System.out.println(receivedAlertTypesIn24Hours);
        
    }
}
