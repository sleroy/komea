
package org.komea.product.backend.service.kpi;



import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.komea.event.factory.JenkinsEventFactory;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.esper.test.EsperQueryTester;
import org.komea.product.backend.exceptions.KpiAlreadyExistingException;
import org.komea.product.backend.service.ISystemProjectBean;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.backend.service.esper.IEventStatisticsService;
import org.komea.product.backend.service.esper.IEventViewerService;
import org.komea.product.backend.service.esper.QueryDefinition;
import org.komea.product.cep.api.ICEPFormula;
import org.komea.product.cep.api.ICEPQueryImplementation;
import org.komea.product.cep.api.IFilterDefinition;
import org.komea.product.cep.filter.NoEventFilter;
import org.komea.product.cep.formula.CountFormula;
import org.komea.product.cep.formula.tuple.TupleCountFormula;
import org.komea.product.cep.formula.tuple.GroupByFormula;
import org.komea.product.cep.query.CEPQueryBuilder;
import org.komea.product.cep.query.FilterDefinition;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
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
    
    
    public static class DemoKPI implements ICEPQueryImplementation
    {
        
        
        /**
         * 
         */
        public DemoKPI() {
        
        
            // TODO Auto-generated constructor stub
        }
        
        
        /*
         * (non-Javadoc)
         * @see org.komea.product.cep.api.ICEPQueryImplementation#getFilterDefinitions()
         */
        @Override
        public List<IFilterDefinition> getFilterDefinitions() {
        
        
            final IFilterDefinition filterDefinition = new FilterDefinition();
            return Collections.singletonList(filterDefinition);
        }
        
        
        /*
         * (non-Javadoc)
         * @see org.komea.product.cep.api.ICEPQueryImplementation#getFormula()
         */
        @Override
        public ICEPFormula getFormula() {
        
        
            return new GroupByFormula(new ProjectEntityKeyTupleCreator(), new TupleCountFormula());
        }
        
        
        /*
         * (non-Javadoc)
         * @see org.komea.product.cep.api.ICEPQueryImplementation#getParameters()
         */
        @Override
        public Map<String, Object> getParameters() {
        
        
            return Collections.EMPTY_MAP;
        }
        
    }
    
    
    
    private static Logger           LOGGER     = LoggerFactory.getLogger(KPIServiceIT.class);
    
    private static final String     TEST_QUERY = "testQuery";
    
    @Autowired
    private IEventEngineService     esperEngine;
    
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
    public void testBug() {
    
    
        final String key = "testBugKPI";
        final Kpi kpi =
                KpiBuilder.createAscending().nameAndKeyDescription(key)
                        .entityType(EntityType.PROJECT).expirationMonth()
                        .query("new " + DemoKPI.class.getName() + "()").cronFiveMinutes().build();
        
        kpiService.saveOrUpdate(kpi);
        
        
        final Measure measure =
                kpiService.getRealTimeMeasure(KpiKey.ofKpiAndEntity(kpi,
                        systemProjectBean.getSystemProject()));
        Assert.assertNotNull("Should be filtered and work", measure);
        
    }
    
    
    @Test
    public void testBug2() {
    
    
        final String key = "testBugKPI2";
        final Kpi kpi =
                KpiBuilder.createAscending().nameAndKeyDescription(key)
                        .entityType(EntityType.PROJECT).expirationMonth()
                        .query("new " + DemoKPI.class.getName() + "()").cronFiveMinutes().build();
        
        kpiService.saveOrUpdate(kpi);
        
        final Measure measure = kpiService.getRealTimeMeasure(KpiKey.ofKpi(kpi));
        Assert.assertNotNull("Should be filtered and work", measure);
        
    }
    
    
    @Test(expected = KpiAlreadyExistingException.class)
    public void testBugAlreadyExistingKPI() {
    
    
        final String key = "testBugKPI3";
        final Kpi kpi =
                KpiBuilder.createAscending().nameAndKeyDescription(key)
                        .entityType(EntityType.PROJECT).expirationMonth()
                        .query("new " + DemoKPI.class.getName() + "()").cronFiveMinutes().build();
        
        kpiService.saveOrUpdate(kpi);
        final Kpi kpi2 =
                KpiBuilder.createAscending().nameAndKeyDescription(key)
                        .entityType(EntityType.PROJECT).expirationMonth()
                        .query("new " + DemoKPI.class.getName() + "()").cronFiveMinutes().build();
        
        kpiService.saveOrUpdate(kpi2);
    }
    
    
    @Test
    public void testBugForIndividualKPI() {
    
    
        final String key = "testBugIndividualKPI";
        final Kpi kpi =
                KpiBuilder.createAscending().nameAndKeyDescription(key)
                        .entityType(EntityType.PROJECT).expirationMonth()
                        .query("new " + DemoKPI.class.getName() + "()").cronFiveMinutes().build();
        kpi.setEntityID(systemProjectBean.getSystemProject().getId());
        kpiService.saveOrUpdate(kpi);
        
        final Measure measure =
                kpiService.getRealTimeMeasure(KpiKey.ofKpiAndEntity(kpi,
                        systemProjectBean.getSystemProject()));
        Assert.assertNotNull("Should work, the results are filtered by the KPI Key", measure);
        final Measure measure2 = kpiService.getRealTimeMeasure(KpiKey.ofKpi(kpi));
        Assert.assertNotNull(
                "Should work, the results are not filtered by the KPI Key (no entity key) but its an individual KPI",
                measure2);
        
    }
    
    
    @Test
    public void testifAlertStatisticsKPIAreWorking() {
    
    
        esperEngine.createQuery(new QueryDefinition(TEST_QUERY, CEPQueryBuilder
                .create(new CountFormula()).defineFilter(new NoEventFilter()).getDefinition()));
        
        
        final IEvent eventToSend =
                EsperQueryTester.convertToEventDTO(new JenkinsEventFactory().sendBuildComplete(
                        "SCERTIFY", 12, "TRUC"));
        for (int i = 0; i < 10; ++i) {
            
            eventPushService.sendEvent(eventToSend);
        }
        
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
    
    
}
