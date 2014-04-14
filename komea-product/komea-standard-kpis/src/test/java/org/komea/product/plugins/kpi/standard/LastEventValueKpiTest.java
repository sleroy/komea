
package org.komea.product.plugins.kpi.standard;



import org.junit.Test;
import org.komea.event.factory.BugZillaEventFactory;
import org.komea.product.backend.esper.test.CEPQueryTester;



public class LastEventValueKpiTest
{
    
    
    @Test
    public final void test() throws Exception {
    
    
        final LastEventValueKpi kpi = new LastEventValueKpi("closed_status_bugs");
        
        final CEPQueryTester newTest = CEPQueryTester.newTest();
        final BugZillaEventFactory eventFactory = new BugZillaEventFactory();
        
        newTest.withQuery(kpi).sendEvent(
                eventFactory.sendEvent("SCERTIFY", 12, "closed_status_bugs"));
        newTest.withQuery(kpi).sendEvent(
                eventFactory.sendEvent("SCERTIFY", 20, "closed_status_bugs"));
        newTest.withQuery(kpi).sendEvent(eventFactory.sendEvent("KOMEA", 50, "closed_status_bugs"));
        newTest.withQuery(kpi).sendEvent(eventFactory.sendEvent("KOMEA", 30, "ncloc"));
        newTest.withQuery(kpi).sendEvent(eventFactory.sendEvent("CIFLOW", 20, "ncloc"));
        
        newTest.dump().hasResults(new Object[][]
            {
                        {
                                newTest.getMockProject().get("SCERTIFY").getEntityKey(),
                                20.0 },
                        {
                                newTest.getMockProject().get("KOMEA").getEntityKey(),
                                50.0 } }).runTest();
        
    }
}
