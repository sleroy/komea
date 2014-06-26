
package org.komea.product.plugins.kpi.standard;



import org.junit.Test;
import org.komea.event.factory.JenkinsEventsFactory;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.product.cep.tester.CEPQueryTester;



public class LastEventValueKpiTest
{
    
    
    @Test
    public final void test() throws Exception {
    
    
        final LastEventValueKpi kpi = new LastEventValueKpi("build_complete", BackupDelay.DAY);
        
        final CEPQueryTester newTest = CEPQueryTester.newTest();
        final JenkinsEventsFactory eventFactory = new JenkinsEventsFactory();
        
        newTest.withQuery(kpi).sendEvent(
                eventFactory.sendBuildComplete("SCERTIFY", 12, "closed_status_bugs"));
        newTest.withQuery(kpi).sendEvent(
                eventFactory.sendBuildComplete("SCERTIFY", 20, "closed_status_bugs"));
        newTest.withQuery(kpi).sendEvent(
                eventFactory.sendBuildComplete("KOMEA", 50, "closed_status_bugs"));
        newTest.withQuery(kpi).sendEvent(eventFactory.sendBuildComplete("KOMEA", 30, "ncloc"));
        
        newTest.dump().hasResults(new Object[][] {
                {
                        newTest.getMockProject().get("SCERTIFY").getEntityKey(), 0.0d }, {
                        newTest.getMockProject().get("KOMEA").getEntityKey(), 0.0 } }).runTest();
        
    }
}
