/**
 * 
 */

package org.komea.product.backend.service.esper.stats;



import org.junit.Assert;
import org.junit.Test;
import org.komea.event.factory.JenkinsEventFactory;
import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.product.cep.tester.CEPQueryTester;
import org.komea.product.database.enums.Severity;
import org.slf4j.LoggerFactory;



/**
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class EventStatisticsServiceTest
{
    
    
    private static final org.slf4j.Logger LOGGER =
                                                         LoggerFactory
                                                                 .getLogger(EventStatisticsServiceTest.class);
    
    
    
    /**
     * Tests the query.
     */
    @Test
    public void testBuildProviderEventFrequencyQuery() {
    
    
        final ICEPQueryImplementation buildProviderEventFrequencyQuery =
                new EventStatisticsService().buildProviderEventFrequencyQuery();
        
        
        new JenkinsEventFactory();
        CEPQueryTester.newTest().withQuery(buildProviderEventFrequencyQuery)
                .sendEvent(JenkinsEventFactory.sendBuildComplete("bla", 12, "truc"), 2)
                .expectRows(1).hasResults(new Object[][]
                    {
                        {
                                "jenkins",
                                "build_complete",
                                2 } }).dump().runTest();
    }
    
    
    @Test
    public void testELQuery() {
    
    
        Assert.assertEquals(
                "new org.komea.product.backend.service.esper.stats.AlertPerSeverityPerDay(T(org.komea.product.database.enums.Severity).BLOCKER)",
                new EventStatisticsService().buildELForAlertCriticityKpi(Severity.BLOCKER));
        Assert.assertEquals(
                "new org.komea.product.backend.service.esper.stats.AlertPerSeverityPerDay(T(org.komea.product.database.enums.Severity).CRITICAL)",
                new EventStatisticsService().buildELForAlertCriticityKpi(Severity.CRITICAL));
        Assert.assertEquals(
                "new org.komea.product.backend.service.esper.stats.AlertPerSeverityPerDay(T(org.komea.product.database.enums.Severity).MAJOR)",
                new EventStatisticsService().buildELForAlertCriticityKpi(Severity.MAJOR));
        Assert.assertEquals(
                "new org.komea.product.backend.service.esper.stats.AlertPerSeverityPerDay(T(org.komea.product.database.enums.Severity).MINOR)",
                new EventStatisticsService().buildELForAlertCriticityKpi(Severity.MINOR));
        Assert.assertEquals(
                "new org.komea.product.backend.service.esper.stats.AlertPerSeverityPerDay(T(org.komea.product.database.enums.Severity).INFO)",
                new EventStatisticsService().buildELForAlertCriticityKpi(Severity.INFO));
        
    }
    
    
    @Test()
    public void testQuerySeverityBlocker() {
    
    
        final CEPQueryTester newTest = CEPQueryTester.newTest();
        new JenkinsEventFactory();
        new JenkinsEventFactory();
        newTest.withQuery(new AlertPerSeverityPerDay(Severity.BLOCKER))
                .sendEvent(JenkinsEventFactory.sendBuildComplete("bla", 12, "truc"), 3)
                .sendEvent(JenkinsEventFactory.sendBuildFailed("bla", 12, "truc"), 2).dump()
                .hasSingleResult(2).dump();
        newTest.getMockEventTypes().get("build_failed").setSeverity(Severity.BLOCKER);
        newTest.runTest();
    }
    
    
    @Test()
    public void testQuerySeverityInfo() {
    
    
        final CEPQueryTester newTest = CEPQueryTester.newTest();
        new JenkinsEventFactory();
        new JenkinsEventFactory();
        newTest.withQuery(new AlertPerSeverityPerDay(Severity.INFO))
                .sendEvent(JenkinsEventFactory.sendBuildComplete("bla", 12, "truc"), 3)
                .sendEvent(JenkinsEventFactory.sendBuildFailed("bla", 12, "truc"), 2).dump()
                .hasSingleResult(3).dump();
        newTest.getMockEventTypes().get("build_failed").setSeverity(Severity.BLOCKER);
        newTest.runTest();
    }
}
