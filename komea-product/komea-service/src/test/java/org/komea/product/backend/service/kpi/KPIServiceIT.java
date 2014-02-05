
package org.komea.product.backend.service.kpi;



import org.junit.Assert;
import org.junit.Test;
import org.komea.product.backend.service.esper.IAlertPushService;
import org.komea.product.backend.service.esper.IAlertStatisticsService;
import org.komea.product.database.alert.AlertBuilder;
import org.komea.product.database.alert.enums.Criticity;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;



public class KPIServiceIT extends AbstractSpringIntegrationTestCase
{
    
    
    @Autowired
    private IKPIService             kpiService;
    
    @Autowired
    private IAlertPushService       alertPushService;
    
    @Autowired
    private IAlertStatisticsService systemProject;
    
    
    
    public KPIServiceIT() {
    
    
        super();
    }
    
    
    @Test
    public void testifAlertStatisticsKPIAreWorking() {
    
    
        for (int i = 0; i < 10; ++i) {
            alertPushService.sendEvent(AlertBuilder.newAlert().category("SYSTEM")
                    .criticity(Criticity.values()[i % Criticity.values().length])
                    .fullMessage("Message of alert").message("Message of alert").project("SYSTEM")
                    .provided("SYSTEM").type("DEMO_ALERT").getAlert());
        }
        Assert.assertTrue(systemProject.getReceivedAlertsIn24LastHours() >= 10);
        
    }
}
