
package org.komea.product.plugins.kpi.standard;



import org.junit.Test;
import org.komea.event.factory.SonarEventFactory;
import org.komea.product.backend.esper.test.CEPQueryTester;



public class SonarMetricKpiTest
{
    
    
    @Test
    public final void testSonarMetricKpi() throws Exception {
    
    
        final SonarMetricKpi buildPerDay = new SonarMetricKpi("cloc");
        
        final CEPQueryTester newTest = CEPQueryTester.newTest();
        final SonarEventFactory sonarEventFactory = new SonarEventFactory();
        
        newTest.withQuery(buildPerDay).sendEvent(
                sonarEventFactory.sendMetricValue("SCERTIFY", 12, "cloc"));
        newTest.withQuery(buildPerDay).sendEvent(
                sonarEventFactory.sendMetricValue("KOMEA", 20, "cloc"));
        newTest.withQuery(buildPerDay).sendEvent(
                sonarEventFactory.sendMetricValue("SCERTIFY", 50, "cloc"));
        
        newTest.dump().hasResults(new Object[][]
            {
                { newTest.getMockProject().get("SCERTIFY").getEntityKey(), 50.0d },
                { newTest.getMockProject().get("KOMEA").getEntityKey(), 20.0d } }
        
        ).runTest();
        
    }
    
    
    @Test
    public final void testSonarMetricKpiWithNullProject() throws Exception {
    
    
        final SonarMetricKpi buildPerDay = new SonarMetricKpi("cloc");
        
        final CEPQueryTester newTest = CEPQueryTester.newTest();
        final SonarEventFactory sonarEventFactory = new SonarEventFactory();
        
        newTest.withQuery(buildPerDay).sendEvent(
                sonarEventFactory.sendMetricValue("SCERTIFY", 12, "cloc"));
        newTest.withQuery(buildPerDay).sendEvent(
                sonarEventFactory.sendMetricValue(null, 20, "cloc"));
        newTest.withQuery(buildPerDay).sendEvent(
                sonarEventFactory.sendMetricValue("SCERTIFY", 50, "cloc"));
        
        newTest.dump().hasResults(new Object[][]
            {
                { newTest.getMockProject().get("SCERTIFY").getEntityKey(), 50.0d },
                { newTest.getMockProject().get("KOMEA").getEntityKey(), 20.0d } }
        
        ).runTest();
        
    }
    
}
