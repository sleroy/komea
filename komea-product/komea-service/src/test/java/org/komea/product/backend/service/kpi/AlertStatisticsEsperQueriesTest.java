
package org.komea.product.backend.service.kpi;



import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.backend.esper.test.EsperQueryTester;
import org.komea.product.database.alert.EventBuilder;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.alert.enums.Criticity;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Provider;



/**
 */
public class AlertStatisticsEsperQueriesTest
{
    
    
    public AlertStatisticsEsperQueriesTest() {
    
    
        super();
    }
    
    
    @Test
    public void testEsperQuery() {
    
    
        final IEvent alert1 = EventBuilder.newAlert().eventType(newEventType("BLA")).build();
        EsperQueryTester.newTest("ALERT_NUMBER")
                .withQuery("SELECT COUNT(*) as alert_number FROM Event.win:time(24 hour)")
                .send(alert1, 4).hasSingleResult("alert_number", Long.valueOf(4)).runTest();
    }
    
    
    @Test
    public void testEsperQuery2() {
    
    
        final IEvent alert1 =
                EventBuilder.newAlert().eventType(newEventType("BLA"))
                        .provided(newProvider("PROV1")).build();
        final IEvent alert2 =
                EventBuilder.newAlert().eventType(newEventType("BLA2"))
                        .provided(newProvider("PROV2")).build();
        final IEvent alert3 =
                EventBuilder.newAlert().eventType(newEventType("BLA3"))
                        .provided(newProvider("PROV3")).build();
        final IEsperEngine esperEngine = EsperQueryTester.newEngine();
        
        final EsperQueryTester test1 =
                EsperQueryTester.newTest("ALERT_NUMBER")
                        .withQuery("SELECT COUNT(*) as value FROM Event").send(alert1, 4)
                        .send(alert2, 3).send(alert3, 2).hasSingleResult("value", Long.valueOf(9));
        final EsperQueryTester test2 =
                EsperQueryTester
                        .newTest("ALERT_NUMBER_2")
                        .withQuery(
                                "SELECT DISTINCT provider, eventType, count(*) as number FROM Event.win:time(24 hour)  GROUP BY provider.name, eventType.name ORDER BY provider.name ASC, eventType.name ASC")
                        .dump().expectRows(3).hasLineResult(new IEsperLineTestPredicate()
                        {
                            
                            
                            @Override
                            public void evaluate(final Map<String, Object> _bean) {
                            
                            
                                Assert.assertEquals("PROV1",
                                        ((Provider) _bean.get("provider")).getName());
                                
                            }
                        }).hasLineResult(new IEsperLineTestPredicate()
                        {
                            
                            
                            @Override
                            public void evaluate(final Map<String, Object> _bean) {
                            
                            
                                Assert.assertEquals("PROV2",
                                        ((Provider) _bean.get("provider")).getName());
                                
                            }
                        }).hasLineResult(new IEsperLineTestPredicate()
                        {
                            
                            
                            @Override
                            public void evaluate(final Map<String, Object> _bean) {
                            
                            
                                Assert.assertEquals("PROV3",
                                        ((Provider) _bean.get("provider")).getName());
                                
                            }
                        });
        
        test1.prepareQuery(esperEngine);
        test2.prepareQuery(esperEngine);
        test1.prepareAlerts(esperEngine);
        test2.prepareAlerts(esperEngine);
        test1.validPredicates();
        test2.validPredicates();
        
    }
    
    
    @Test
    public void testEsperQuery3() {
    
    
        final IEvent alert1 = EventBuilder.newAlert().eventType(newEventType("BLA")).build();
        EsperQueryTester
                .newTest("ALERT_NUMBER")
                .withQuery(
                        "SELECT COUNT(*) as alert_number FROM Event.win:time(24 hour) WHERE eventType.severity=Severity."
                                + Criticity.INFO.name()).send(alert1, 4)
                .hasSingleResult("alert_number", Long.valueOf(4)).runTest();
    }
    
    
    /**
     * Method newEventType.
     * @param _string String
     * @return EventType
     */
    private EventType newEventType(final String _string) {
    
    
        final EventType eventType = new EventType();
        eventType.setName(_string);
        eventType.setSeverity(Severity.INFO);
        return eventType;
    }
    
    
    /**
     * Method newProvider.
     * @param _string String
     * @return Provider
     */
    private Provider newProvider(final String _string) {
    
    
        final Provider provider = new Provider();
        provider.setId(_string.hashCode());
        provider.setName(_string);
        
        return provider;
    }
}
