
package org.komea.product.plugins.kpi.standard;



import org.junit.Test;
import org.komea.event.factory.JenkinsEventFactory;
import org.komea.product.backend.esper.test.CEPQueryTester;



public class BuildPerMonthTest
{
    
    
    @Test
    public final void testBuildPerMonth() throws Exception {
    
    
        final BuildPerMonth buildPerDay = new BuildPerMonth();
        final CEPQueryTester newTest = CEPQueryTester.newTest();
        newTest.withQuery(buildPerDay).sendEvent(
                new JenkinsEventFactory().sendBuildStarted("SCERTIFY", 1, "TRUC"));
        newTest.withQuery(buildPerDay).sendEvent(
                new JenkinsEventFactory().sendBuildStarted("KOMEA", 1, "TRUC"));
        newTest.withQuery(buildPerDay).sendEvent(
                new JenkinsEventFactory().sendBuildStarted("ALBRAND", 1, "TRUC"));
        newTest.withQuery(buildPerDay).sendEvent(
                new JenkinsEventFactory().sendBuildStarted("KOMEA", 1, "TRUC"));
        newTest.dump().hasResults(new Object[][] {
                {
                        newTest.getMockProject().get("SCERTIFY").getEntityKey(), 1 }, {
                        newTest.getMockProject().get("KOMEA").getEntityKey(), 2 }, {
                        newTest.getMockProject().get("ALBRAND").getEntityKey(), 1 } }
        
        ).runTest();
        
        
    }
}
