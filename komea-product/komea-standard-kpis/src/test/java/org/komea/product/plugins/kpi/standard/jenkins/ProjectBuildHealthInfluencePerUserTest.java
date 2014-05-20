
package org.komea.product.plugins.kpi.standard.jenkins;



import org.junit.Test;
import org.komea.event.factory.JenkinsEventsFactory;
import org.komea.product.cep.tester.CEPQueryTester;



public class ProjectBuildHealthInfluencePerUserTest
{
    
    
    @Test
    public final void ProjectHealthInfluencePerUserTest() throws Exception {
    
    
        final ProjectBuildHealthInfluencePerUser buildPerDay =
                new ProjectBuildHealthInfluencePerUser();
        
        final CEPQueryTester newTest = CEPQueryTester.newTest();
        
        final CEPQueryTester withQuery = newTest.withQuery(buildPerDay);
        
        withQuery.sendEvent(JenkinsEventsFactory.sendBuildStarted("SCERTIFY", 1, "TRUC", "sleroy"));
        withQuery.sendEvent(JenkinsEventsFactory.sendBuildComplete("KOMEA", 1, "TRUC", "yoip"));
        withQuery.sendEvent(JenkinsEventsFactory.sendBuildFixed("SCERTIFY", 1, "TRUC", "sleroy"));
        withQuery.sendEvent(JenkinsEventsFactory.sendBuildBroken("ALBRAND", 1, "TRUC", "sleroy"));
        withQuery.sendEvent(JenkinsEventsFactory.sendBuildFixed("KOMEA", 1, "TRUC", "yoip"));
        withQuery.sendEvent(JenkinsEventsFactory.sendBuildFixed("KOMEA", 1, "TRUC", "yoip"));
        withQuery.sendEvent(JenkinsEventsFactory.sendBuildBroken("KOMEA", 1, "TRUC", "yoip"));
        newTest.dump().hasResults(new Object[][] {
                {
                        newTest.getMockPerson().get("sleroy").getEntityKey(), 0.0 }, {
                        newTest.getMockPerson().get("yoip").getEntityKey(), 1.0 } }
        
        ).runTest();
    }
    
}
