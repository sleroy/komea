/**
 * 
 */

package org.komea.product.backend.service.esper.stats;



import org.junit.Assert;
import org.junit.Test;
import org.komea.event.factory.JenkinsEventFactory;
import org.komea.product.backend.esper.test.EsperQueryTester;
import org.komea.product.cep.api.IQueryDefinition;
import org.komea.product.cep.formula.ElFormula;
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
    
    
        final IQueryDefinition buildProviderEventFrequencyQuery =
                new EventStatisticsService().buildProviderEventFrequencyQuery();
        
        
        EsperQueryTester.newTest().withQuery(buildProviderEventFrequencyQuery)
                .sendEvent(new JenkinsEventFactory().sendBuildComplete("bla", 12, "truc"), 2)
                .expectRows(1).hasResults(new Object[][] {
                    {
                            "jenkins", "build_complete", 2 } }).dump().runTest();
    }
    
    
    @Test
    public void testELQuery() {
    
    
        Assert.assertEquals("new "
                + AlertPerSeverityPerDay.class.getName()
                + "(org.komea.product.database.enums.Severity.BLOCKER)",
                new EventStatisticsService().buildELForAlertCriticityKpi(Severity.BLOCKER));
        Assert.assertEquals("new "
                + AlertPerSeverityPerDay.class.getName()
                + "(org.komea.product.database.enums.Severity.CRITICAL)",
                new EventStatisticsService().buildELForAlertCriticityKpi(Severity.CRITICAL));
        Assert.assertEquals("new "
                + AlertPerSeverityPerDay.class.getName()
                + "(org.komea.product.database.enums.Severity.MAJOR)",
                new EventStatisticsService().buildELForAlertCriticityKpi(Severity.MAJOR));
        Assert.assertEquals("new "
                + AlertPerSeverityPerDay.class.getName()
                + "(org.komea.product.database.enums.Severity.MINOR)",
                new EventStatisticsService().buildELForAlertCriticityKpi(Severity.MINOR));
        Assert.assertEquals("new "
                + AlertPerSeverityPerDay.class.getName()
                + "(org.komea.product.database.enums.Severity.INFO)",
                new EventStatisticsService().buildELForAlertCriticityKpi(Severity.INFO));
        
    }
    
    
    @Test
    public void testELQueryCreation() {
    
    
        final ElFormula<IQueryDefinition> elFormula =
                new ElFormula<IQueryDefinition>(
                        new EventStatisticsService().buildELForAlertCriticityKpi(Severity.BLOCKER),
                        null);
        Assert.assertNotNull(elFormula);
        
    }
    
    
    @Test()
    public void testQuerySeverityBlocker() {
    
    
        final EsperQueryTester newTest = EsperQueryTester.newTest();
        newTest.withQuery(new AlertPerSeverityPerDay(Severity.BLOCKER))
                .sendEvent(new JenkinsEventFactory().sendBuildComplete("bla", 12, "truc"), 3)
                .sendEvent(new JenkinsEventFactory().sendBuildFailed("bla", 12, "truc"), 2).dump()
                .hasSingleResult(2).dump();
        newTest.getMockEventTypes().get("build_failed").setSeverity(Severity.BLOCKER);
        newTest.runTest();
    }
    
    
    @Test()
    public void testQuerySeverityInfo() {
    
    
        final EsperQueryTester newTest = EsperQueryTester.newTest();
        newTest.withQuery(new AlertPerSeverityPerDay(Severity.INFO))
                .sendEvent(new JenkinsEventFactory().sendBuildComplete("bla", 12, "truc"), 3)
                .sendEvent(new JenkinsEventFactory().sendBuildFailed("bla", 12, "truc"), 2).dump()
                .hasSingleResult(3).dump();
        newTest.getMockEventTypes().get("build_failed").setSeverity(Severity.BLOCKER);
        newTest.runTest();
    }
}
