/**
 * 
 */

package org.komea.product.backend.service.esper.stats;



import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.komea.event.factory.JenkinsEventFactory;
import org.komea.product.backend.esper.test.EsperQueryTester;
import org.komea.product.backend.service.ISystemProjectBean;
import org.komea.product.backend.service.esper.EventEngineService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.enums.Severity;
import org.komea.product.service.dto.EventTypeStatistic;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class EventStatisticsServiceIT extends AbstractSpringIntegrationTestCase
{
    
    
    private static final org.slf4j.Logger LOGGER = LoggerFactory
                                                         .getLogger(EventStatisticsServiceIT.class);
    
    
    @Autowired
    private EventStatisticsService        alertStats;
    
    @Autowired
    private EventEngineService            esperEngine;
    
    @Autowired
    private IKPIService                   kpiService;
    
    @Autowired
    private ISystemProjectBean            systemProject;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.esper.stats.EventStatisticsService#getReceivedAlertTypesIn24LastHours()}.
     */
    @Test
    public final void testGetReceivedAlertTypesIn24LastHours() {
    
    
        final JenkinsEventFactory jenkinsEventFactory = new JenkinsEventFactory();
        final IEvent convertEventDTO =
                EsperQueryTester.convertToEventDTO(jenkinsEventFactory.sendBuildFailed("SCERTIFY",
                        448, "truc"));
        convertEventDTO.getEventType().setSeverity(Severity.INFO);
        LOGGER.info("EVENT SENT {}", convertEventDTO);
        esperEngine.sendEvent(convertEventDTO);
        esperEngine.sendEvent(convertEventDTO);
        esperEngine.sendEvent(convertEventDTO);
        esperEngine.sendEvent(convertEventDTO);
        
        final List<EventTypeStatistic> receivedAlertTypesIn24Hours =
                alertStats.getReceivedAlertTypesIn24LastHours();
        LOGGER.info("Stats {}", receivedAlertTypesIn24Hours);
        final EventTypeStatistic alertTypeStatistic = receivedAlertTypesIn24Hours.get(0);
        LOGGER.info("Alert number {}", alertTypeStatistic.getValue());
        Assert.assertTrue(4L <= alertTypeStatistic.getValue());
        
        boolean found = false;
        for (final EventTypeStatistic stat : receivedAlertTypesIn24Hours) {
            found |= convertEventDTO.getEventType().getEventKey().equals(stat.getType());
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
