
package org.komea.product.backend.service.kpi;



import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.backend.service.esper.IAlertPushService;
import org.komea.product.backend.service.esper.IAlertStatisticsService;
import org.komea.product.backend.service.esper.IAlertViewerService;
import org.komea.product.backend.service.esper.QueryDefinition;
import org.komea.product.database.alert.AlertBuilder;
import org.komea.product.database.alert.IAlert;
import org.komea.product.database.alert.enums.Criticity;
import org.komea.product.service.dto.AlertTypeStatistic;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



public class KPIServiceIT extends AbstractSpringIntegrationTestCase
{
    
    
    private static final String     ALERT_TYPE = "KpiServiceIT_TYPE";
    
    private static final String     TEST_QUERY = "testQuery";
    
    @Autowired
    private IKPIService             kpiService;
    
    @Autowired
    private IAlertPushService       alertPushService;
    
    @Autowired
    private IAlertStatisticsService systemProject;
    
    @Autowired
    private IEsperEngine            esperEngine;
    
    private static Logger           LOGGER     = LoggerFactory.getLogger(KPIServiceIT.class);
    
    @Autowired
    private IAlertViewerService     viewerService;
    
    
    
    public KPIServiceIT() {
    
    
        super();
    }
    
    
    @Test
    public void testifAlertStatisticsKPIAreWorking() {
    
    
        esperEngine.createEPL(new QueryDefinition("SELECT * FROM Alert", TEST_QUERY));
        
        for (int i = 0; i < 10; ++i) {
            alertPushService.sendEvent(AlertBuilder.newAlert().category("SYSTEM")
                    .criticity(Criticity.values()[i % Criticity.values().length])
                    .fullMessage("Message of alert").message("Message of alert").project("SYSTEM")
                    .provided("SYSTEM").type(ALERT_TYPE).getAlert());
        }
        final long numberAlerts = systemProject.getReceivedAlertsIn24LastHours();
        LOGGER.info("Received alerts {}", numberAlerts);
        Assert.assertTrue(numberAlerts >= 10);
        final List<AlertTypeStatistic> receivedAlertTypesIn24LastHours =
                systemProject.getReceivedAlertTypesIn24LastHours();
        LOGGER.info("Received alerts {}", receivedAlertTypesIn24LastHours);
        // On récupère la liste des alertes reçues dans ce laps de temps, pour vérifier que Esper a bien reçu nos alertes
        final List<IAlert> instantView = viewerService.getInstantView(TEST_QUERY);
        boolean found = false;
        for (final IAlert alert : instantView) {
            found |= ALERT_TYPE.equals(alert.getType());
        }
        Assert.assertTrue("We received alerts from the corresponding type", found);
        found = false;
        for (final AlertTypeStatistic stat : receivedAlertTypesIn24LastHours) {
            found |= ALERT_TYPE.equals(stat.getType());
        }
        Assert.assertTrue("Alert is not found", found);
        
    }
    
}
