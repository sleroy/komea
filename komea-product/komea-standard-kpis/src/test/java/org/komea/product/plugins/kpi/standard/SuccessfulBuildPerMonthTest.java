
package org.komea.product.plugins.kpi.standard;



import org.junit.Test;
import org.komea.event.factory.JenkinsEventFactory;
import org.komea.product.backend.esper.test.CEPQueryTester;



public class SuccessfulBuildPerMonthTest
{
    
    
    @Test
    public final void testSuccessfulBuildPerMonth() throws Exception {
    
    
        final SuccessfulBuildPerMonth buildPerDay = new SuccessfulBuildPerMonth();
        
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
                    newTest.getMockProject().get("KOMEA").getEntityKey(), 1 } }
        
        ).runTest();
    }
    
}
