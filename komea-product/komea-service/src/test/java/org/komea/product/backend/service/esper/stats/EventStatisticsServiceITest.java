/**
 * 
 */

package org.komea.product.backend.service.esper.stats;



import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.komea.event.factory.JenkinsEventFactory;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.api.IKPIService;
import org.komea.product.backend.service.ISystemProjectBean;
import org.komea.product.backend.service.esper.IEventStatisticsService;
import org.komea.product.cep.tester.CEPQueryTester;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.enums.Severity;
import org.komea.product.service.dto.EventTypeStatistic;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class EventStatisticsServiceITest extends AbstractSpringIntegrationTestCase
{
    
    
    @Autowired
    private IEventStatisticsService alertStats;
    
    @Autowired
    private IEventEngineService     esperEngine;
    
    @Autowired
    private IKPIService             kpiService;
    
    @Autowired
    private ISystemProjectBean      systemProject;
    
    
    
    /**
     * Test method for {@link org.komea.product.cep.tuples.EventStatisticsService#getReceivedAlertTypesIn24LastHours()} .
     */
    @Test
    public final void testGetReceivedAlertTypesIn24LastHours() {
    
    
        final CEPQueryTester newTest = CEPQueryTester.newTest();
        
        LOGGER.info("EVENT SENT {}",
                newTest.convertDto(JenkinsEventFactory.sendBuildFailed("SCERTIFY", 448, "truc")));
        final IEvent convertDto =
                newTest.convertDto(JenkinsEventFactory.sendBuildFailed("SCERTIFY", 448, "truc"));
        newTest.getMockEventTypes().get("build_failed").setSeverity(Severity.INFO);
        esperEngine.sendEvent(convertDto);
        esperEngine.sendEvent(newTest.convertDto(JenkinsEventFactory.sendBuildFailed("SCERTIFY",
                448, "truc")));
        esperEngine.sendEvent(newTest.convertDto(JenkinsEventFactory.sendBuildFailed("SCERTIFY",
                448, "truc")));
        esperEngine.sendEvent(newTest.convertDto(JenkinsEventFactory.sendBuildFailed("SCERTIFY",
                448, "truc")));
        
        final List<EventTypeStatistic> receivedAlertTypesIn24Hours =
                alertStats.getReceivedAlertTypesIn24LastHours();
        LOGGER.info("Stats {}", receivedAlertTypesIn24Hours);
        final EventTypeStatistic alertTypeStatistic = receivedAlertTypesIn24Hours.get(0);
        LOGGER.info("Alert number {}", alertTypeStatistic.getValue());
        System.out.println(alertTypeStatistic.getValue());
        // Assert.assertTrue(5L <= alertTypeStatistic.getValue());
        
        boolean found = false;
        for (final EventTypeStatistic stat : receivedAlertTypesIn24Hours) {
            found |= stat.getType().equals("build_failed");
        }
        Assert.assertEquals(4L, alertStats.getNumberOfAlerts(Severity.INFO));
        Assert.assertEquals(0L, alertStats.getNumberOfAlerts(Severity.MAJOR));
        Assert.assertEquals(0L, alertStats.getNumberOfAlerts(Severity.MINOR));
        Assert.assertEquals(0L, alertStats.getNumberOfAlerts(Severity.CRITICAL));
        Assert.assertEquals(0L, alertStats.getNumberOfAlerts(Severity.BLOCKER));
        Assert.assertTrue("Event is not found", found);
        ;
        
    }
    
}
