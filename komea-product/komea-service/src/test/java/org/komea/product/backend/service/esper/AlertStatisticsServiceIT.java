/**
 * 
 */

package org.komea.product.backend.service.esper;



import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.komea.event.factory.JenkinsEventFactory;
import org.komea.product.backend.esper.test.EsperQueryTester;
import org.komea.product.backend.service.ISystemProjectBean;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.alert.IEvent;
import org.komea.product.service.dto.AlertTypeStatistic;
import org.komea.product.service.dto.KpiKey;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author sleroy
 */
public class AlertStatisticsServiceIT extends AbstractSpringIntegrationTestCase
{
    
    
    @Autowired
    private AlertStatisticsService alertStats;
    
    @Autowired
    private EsperEngineBean        esperEngine;
    
    @Autowired
    private IKPIService            kpiService;
    
    @Autowired
    private ISystemProjectBean     systemProject;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.esper.AlertStatisticsService#getReceivedAlertTypesIn24LastHours()}.
     */
    @Test
    public final void testGetReceivedAlertTypesIn24LastHours() {
    
    
        esperEngine.registerListener(AlertStatisticsService.STATS_BREAKDOWN_24H,
                new LogUpdateListener(AlertStatisticsService.STATS_BREAKDOWN_24H));
        
        ;
        
        esperEngine.registerListener(
                kpiService.findKPIOrFail(
                        KpiKey.ofKpiNameAndEntity(AlertStatisticsService.ALERT_RECEIVED_IN_ONE_DAY,
                                systemProject.getSystemProject())).computeKPIEsperKey(),
                new LogUpdateListener(AlertStatisticsService.ALERT_RECEIVED_IN_ONE_DAY));
        
        final JenkinsEventFactory jenkinsEventFactory = new JenkinsEventFactory();
        final IEvent convertEventDTO =
                EsperQueryTester.convertEventDTO(jenkinsEventFactory.sendBuildFailed("SCERTIFY",
                        448, "truc"));
        esperEngine.sendEvent(convertEventDTO);
        esperEngine.sendEvent(convertEventDTO);
        esperEngine.sendEvent(convertEventDTO);
        esperEngine.sendEvent(convertEventDTO);
        
        final List<AlertTypeStatistic> receivedAlertTypesIn24Hours =
                alertStats.getReceivedAlertTypesIn24LastHours();
        
        final AlertTypeStatistic alertTypeStatistic = receivedAlertTypesIn24Hours.get(0);
        Assert.assertTrue(4L <= alertTypeStatistic.getNumber());
        
        boolean found = false;
        for (final AlertTypeStatistic stat : receivedAlertTypesIn24Hours) {
            found |= convertEventDTO.getEventType().getEventKey().equals(stat.getType());
        }
        Assert.assertTrue("Event is not found", found);
        ;
        
    }
}
