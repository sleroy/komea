
package org.komea.product.plugins.kpi.standard;



import org.junit.Test;
import org.komea.event.factory.JenkinsEventFactory;
import org.komea.product.backend.esper.test.CEPQueryTester;



public class SuccessfulBuildPerDayTest
{
    
    
    @Test
    public final void testSuccessfulBuildPerDay() throws Exception {
    
    
        final SuccessfulBuildPerDay buildPerDay = new SuccessfulBuildPerDay();
        
        final CEPQueryTester newTest = CEPQueryTester.newTest();
        
        newTest.withQuery(buildPerDay).sendEvent(
                new JenkinsEventFactory().sendBuildStarted("SCERTIFY", 1, "TRUC"));
        newTest.withQuery(buildPerDay).sendEvent(
                new JenkinsEventFactory().sendBuildComplete("KOMEA", 1, "TRUC"));
        newTest.withQuery(buildPerDay).sendEvent(
                new JenkinsEventFactory().sendBuildStarted("ALBRAND", 1, "TRUC"));
        newTest.withQuery(buildPerDay).sendEvent(
                new JenkinsEventFactory().sendBuildStarted("KOMEA", 1, "TRUC"));
        newTest.dump().hasResults(new Object[][] {
                {
                        newTest.getMockProject().get("SCERTIFY").getEntityKey(), 0 }, {
                        newTest.getMockProject().get("KOMEA").getEntityKey(), 1 }, {
                        newTest.getMockProject().get("ALBRAND").getEntityKey(), 0 } }
        
        ).runTest();
    }
}
