
package org.komea.product.backend.service.kpi;



import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.komea.event.factory.JenkinsEventFactory;
import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.backend.esper.test.EsperQueryTester;
import org.komea.product.backend.service.ISystemProjectBean;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.backend.service.esper.IEventStatisticsService;
import org.komea.product.backend.service.esper.IEventViewerService;
import org.komea.product.backend.service.esper.QueryDefinition;
import org.komea.product.database.alert.EventDtoBuilder;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.dto.KpiTendancyDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Project;
import org.komea.product.service.dto.EventTypeStatistic;
import org.komea.product.service.dto.KpiKey;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 */
public class KPIServiceIT extends AbstractSpringIntegrationTestCase
{
    
    
    private static Logger           LOGGER     = LoggerFactory.getLogger(KPIServiceIT.class);
    
    private static final String     TEST_QUERY = "testQuery";
    
    @Autowired
    private IEsperEngine            esperEngine;
    
    @Autowired
    private IEventPushService       eventPushService;
    
    @Autowired
    private IEventStatisticsService eventStatisticsService;
    
    @Autowired
    private IKPIService             kpiService;
    
    @Autowired
    private ISystemProjectBean      systemProjectBean;
    
    
    @Autowired
    private IEventViewerService     viewerService;
    
    
    
    public KPIServiceIT() {
    
    
        super();
    }
    
    
    @Test
    public void testifAlertStatisticsKPIAreWorking() {
    
    
        esperEngine.createEPL(new QueryDefinition("SELECT * FROM Event", TEST_QUERY));
        
        
        final IEvent eventToSend =
                EsperQueryTester.convertToEventDTO(new JenkinsEventFactory().sendBuildComplete(
                        "SCERTIFY", 12, "TRUC"));
        for (int i = 0; i < 10; ++i) {
            
            eventPushService.sendEvent(eventToSend);
        }
        System.out.println(kpiService.listAllKpis());
        final long numberAlerts = eventStatisticsService.getReceivedAlertsIn24LastHours();
        LOGGER.info("Received alerts {}", numberAlerts);
        Assert.assertTrue(numberAlerts >= 10);
        final List<EventTypeStatistic> receivedAlertTypesIn24LastHours =
                eventStatisticsService.getReceivedAlertTypesIn24LastHours();
        LOGGER.info("Received alerts {}", receivedAlertTypesIn24LastHours);
        // On récupère la liste des alertes reçues dans ce laps de temps, pour vérifier que Esper a bien reçu nos alertes
        final List<IEvent> instantView = viewerService.getInstantView(TEST_QUERY);
        boolean found = false;
        for (final IEvent event : instantView) {
            found |=
                    eventToSend.getEventType().getEventKey()
                            .equals(event.getEventType().getEventKey());
        }
        Assert.assertTrue("We received alerts from the corresponding type", found);
        found = false;
        for (final EventTypeStatistic stat : receivedAlertTypesIn24LastHours) {
            found |= eventToSend.getEventType().getEventKey().equals(stat.getType());
        }
        Assert.assertTrue("Event is not found", found);
        
    }
    
    
    @Test
    public void testTendancy() {
    
    
        final String key = "Tendancy example KPI";
        final Kpi kpi =
                KpiBuilder
                        .createAscending()
                        .nameAndKeyDescription(key)
                        .entityType(EntityType.PROJECT)
                        .expirationMonth()
                        .query("SELECT project as entity, COUNT(*) as value FROM Event.win:time(1 day) GROUP BY project")
                        .cronFiveMinutes().build();
        
        kpiService.saveOrUpdate(kpi);
        final EventSimpleDto event =
                EventDtoBuilder.newAlert().eventType("event-demo").provided("/demoProvider")
                        .message("Send event").project("SYSTEM").build();
        
        
        final KpiKey ofKpiName = KpiKey.ofKpiName(kpi.getKpiKey());
        eventPushService.sendEventDto(event);
        final KpiTendancyDto kpiTendancy = kpiService.getKpiTendancy(ofKpiName);
        final Project systemProject = systemProjectBean.getSystemProject();
        Assert.assertTrue(kpiTendancy.getLineValueDtos().size() > 0);
        Assert.assertNotNull(kpiTendancy.getRealValue(systemProject));
        Assert.assertNull(kpiTendancy.getLastValue(systemProject));
        
        // Seconds storage
        eventPushService.sendEventDto(event);
        eventPushService.sendEventDto(event);
        kpiService.storeValueInHistory(ofKpiName);
        
        Assert.assertNotNull(kpiTendancy.getRealValue(systemProject));
        Assert.assertNotNull(kpiTendancy.getLastValue(systemProject));
        
        
        System.out.println(kpiTendancy);
        ;
        
        
    }
}
