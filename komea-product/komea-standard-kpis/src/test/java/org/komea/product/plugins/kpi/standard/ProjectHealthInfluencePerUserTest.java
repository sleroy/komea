
package org.komea.product.plugins.kpi.standard;



import org.junit.Test;
import org.komea.event.factory.JenkinsEventsFactory;
import org.komea.product.cep.tester.CEPQueryTester;



public class ProjectHealthInfluencePerUserTest
{
    
    
    @Test 
    public final void ProjectHealthInfluencePerUserTest() throws Exception {
    
    
        final ProjectHealthInfluencePerUser buildPerDay = new ProjectHealthInfluencePerUser();
        
        final CEPQueryTester newTest = CEPQueryTester.newTest();
        
        final CEPQueryTester withQuery = newTest.withQuery(buildPerDay);
        
        withQuery.sendEvent(JenkinsEventsFactory.sendBuildStarted("SCERTIFY", 1, "TRUC", "sleroy"));
        withQuery.sendEvent(JenkinsEventsFactory.sendBuildComplete("KOMEA", 1, "TRUC", "yoip"));
        withQuery.sendEvent(JenkinsEventsFactory.sendBuildFixed("SCERTIFY", 1, "TRUC", "sleroy"));
        withQuery.sendEvent(JenkinsEventsFactory.sendBuildBroken("ALBRAND", 1, "TRUC", "sleroy"));
        withQuery.sendEvent(JenkinsEventsFactory.sendBuildFixed("KOMEA", 1, "TRUC", "yoip"));
        withQuery.sendEvent(JenkinsEventsFactory.sendBuildFixed("KOMEA", 1, "TRUC", "yoip"));
        withQuery.sendEvent(JenkinsEventsFactory.sendBuildBroken("KOMEA", 1, "TRUC", "yoip"));
        newTest.dump().hasResults(new Object[][]
            {
                { newTest.getMockPerson().get("sleroy").getEntityKey(), 50.0 },
                { newTest.getMockPerson().get("yoip").getEntityKey(), 100.0 * 2.0 / 3.0 } }
        
        ).runTest();
    }
    
}
