
package org.komea.product.plugins.kpi.standard.jenkins;



import org.junit.Test;
import org.komea.event.factory.JenkinsEventsFactory;
import org.komea.product.cep.tester.CEPQueryTester;
import org.komea.product.plugins.kpi.standard.jenkins.NumberOfSuccessfulBuildPerDay;



public class NumberOfSuccessfulBuildPerDayTest
{
    
    
    @Test 
    public final void testSuccessfulBuildPerDay() throws Exception {
    
    
        final NumberOfSuccessfulBuildPerDay buildPerDay = new NumberOfSuccessfulBuildPerDay();
        
        final CEPQueryTester newTest = CEPQueryTester.newTest();
        
        newTest.withQuery(buildPerDay).sendEvent(
                JenkinsEventsFactory.sendBuildStarted("SCERTIFY", 1, "TRUC"));
        newTest.withQuery(buildPerDay).sendEvent(
                JenkinsEventsFactory.sendBuildComplete("KOMEA", 1, "TRUC"));
        newTest.withQuery(buildPerDay).sendEvent(
                JenkinsEventsFactory.sendBuildStarted("ALBRAND", 1, "TRUC"));
        newTest.withQuery(buildPerDay).sendEvent(
                JenkinsEventsFactory.sendBuildStarted("KOMEA", 1, "TRUC"));
        newTest.dump().hasResults(new Object[][]
            {
                { newTest.getMockProject().get("KOMEA").getEntityKey(), 1 } }
        
        ).runTest();
    }
}
