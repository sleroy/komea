
package org.komea.product.plugins.kpi.standard;



import org.junit.Test;
import org.komea.event.factory.JenkinsEventFactory;
import org.komea.product.backend.esper.test.CEPQueryTester;



public class BuildPerDayTest
{
    
    
    @Test
    public final void testBuildPerDay() throws Exception {
    
    
        final BuildPerDay buildPerDay = new BuildPerDay();
        final CEPQueryTester newTest = CEPQueryTester.newTest();
        final JenkinsEventFactory jenkinsEventFactory = new JenkinsEventFactory();
        newTest.withQuery(buildPerDay).sendEvent(
                jenkinsEventFactory.sendBuildStarted("SCERTIFY", 1, "TRUC"));
        newTest.withQuery(buildPerDay).sendEvent(
                jenkinsEventFactory.sendBuildStarted("KOMEA", 1, "TRUC"));
        newTest.withQuery(buildPerDay).sendEvent(
                jenkinsEventFactory.sendBuildStarted("ALBRAND", 1, "TRUC"));
        newTest.withQuery(buildPerDay).sendEvent(
                jenkinsEventFactory.sendBuildStarted("KOMEA", 1, "TRUC"));
        newTest.dump().hasResults(new Object[][] {
                {
                        newTest.getMockProject().get("SCERTIFY").getEntityKey(), 1 }, {
                        newTest.getMockProject().get("KOMEA").getEntityKey(), 2 }, {
                        newTest.getMockProject().get("ALBRAND").getEntityKey(), 1 } }
        
        ).runTest();
        
        
    }
}
