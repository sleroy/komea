
package org.komea.product.backend.service.kpi;



import org.junit.Test;
import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.database.alert.AlertBuilder;
import org.komea.product.database.alert.IAlert;
import org.komea.product.database.alert.enums.Criticity;



public class AlertStatisticsEsperQueriesTest
{
    
    
    public AlertStatisticsEsperQueriesTest() {
    
    
        super();
    }
    
    
    @Test
    public void testEsperQuery() {
    
    
        final IAlert alert1 = AlertBuilder.newAlert().type("BLA").build();
        EsperQueryTester.newTest("ALERT_NUMBER")
                .withQuery("SELECT COUNT(*) as alert_number FROM Alert.win:time(24 hour)")
                .send(alert1, 4).hasSingleResult("alert_number", Long.valueOf(4)).runTest();
    }
    
    
    @Test
    public void testEsperQuery2() {
    
    
        final IAlert alert1 = AlertBuilder.newAlert().type("BLA").provided("PROV1").build();
        final IAlert alert2 = AlertBuilder.newAlert().type("BLA2").provided("PROV2").build();
        final IAlert alert3 = AlertBuilder.newAlert().type("BLA3").provided("PROV3").build();
        final IEsperEngine esperEngine = EsperQueryTester.newEngine();
        
        final EsperQueryTester test1 =
                EsperQueryTester.newTest("ALERT_NUMBER")
                        .withQuery("SELECT COUNT(*) as value FROM Alert").send(alert1, 4)
                        .send(alert2, 3).send(alert3, 2).hasSingleResult("value", Long.valueOf(9));
        final EsperQueryTester test2 =
                EsperQueryTester
                        .newTest("ALERT_NUMBER_2")
                        .withQuery(
                                "SELECT  provider, type, count(*) as number FROM Alert.win:time(24 hour)  GROUP BY provider, type ORDER BY provider ASC, type ASC")
                        .dump().hasLineResult("provider", "PROV1").expectRows(3)
                        .hasLineResult("provider", "PROV2").hasLineResult("provider", "PROV3");
        
        test1.prepareQuery(esperEngine);
        test2.prepareQuery(esperEngine);
        test1.prepareAlerts(esperEngine);
        test2.prepareAlerts(esperEngine);
        test1.validPredicates();
        test2.validPredicates();
        
    }
    
    
    @Test
    public void testEsperQuery3() {
    
    
        final IAlert alert1 = AlertBuilder.newAlert().type("BLA").criticity(Criticity.INFO).build();
        EsperQueryTester
                .newTest("ALERT_NUMBER")
                .withQuery(
                        "SELECT COUNT(*) as alert_number FROM Alert.win:time(24 hour) WHERE criticity=Criticity."
                                + Criticity.INFO.name()).send(alert1, 4)
                .hasSingleResult("alert_number", Long.valueOf(4)).runTest();
    }
}
