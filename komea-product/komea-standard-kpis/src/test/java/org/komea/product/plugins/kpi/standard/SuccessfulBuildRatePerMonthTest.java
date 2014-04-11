
package org.komea.product.plugins.kpi.standard;



import org.junit.Test;
import org.komea.event.factory.JenkinsEventsFactory;
import org.komea.product.backend.esper.test.CEPQueryTester;



public class SuccessfulBuildRatePerMonthTest
{
    
    
    @Test @Ignore
    public final void testSuccessfulBuildRatePerMonth() throws Exception {
    
    
        final SuccessfulBuildRatePerMonth buildPerDay = new SuccessfulBuildRatePerMonth();
        
        final CEPQueryTester newTest = CEPQueryTester.newTest();
        
        final CEPQueryTester withQuery = newTest.withQuery(buildPerDay);
        
        withQuery.sendEvent(JenkinsEventsFactory.sendBuildStarted("SCERTIFY", 1, "TRUC"));
        withQuery.sendEvent(JenkinsEventsFactory.sendBuildComplete("KOMEA", 1, "TRUC"));
        
        withQuery.sendEvent(JenkinsEventsFactory.sendBuildStarted("ALBRAND", 1, "TRUC"));
        withQuery.sendEvent(JenkinsEventsFactory.sendBuildStarted("KOMEA", 1, "TRUC"));
        withQuery.sendEvent(JenkinsEventsFactory.sendBuildStarted("KOMEA", 1, "TRUC"));
        withQuery.sendEvent(JenkinsEventsFactory.sendBuildStarted("KOMEA", 1, "TRUC"));
        newTest.hasResults(new Object[][]
            {
                { newTest.getMockProject().get("SCERTIFY").getEntityKey(), 0.0f },
                { newTest.getMockProject().get("KOMEA").getEntityKey(), 100f / 3 },
                { newTest.getMockProject().get("ALBRAND").getEntityKey(), 0.0f } }
        
        ).runTest();
    }
    
}
