/**
 * 
 */

package org.komea.product.backend.service.esper;



import org.junit.Test;
import org.komea.event.factory.JenkinsEventFactory;
import org.komea.product.backend.esper.test.EsperQueryTester;
import org.slf4j.LoggerFactory;



/**
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class EventStatisticsServiceTest
{
    
    
    private static final org.slf4j.Logger LOGGER =
                                                         LoggerFactory
                                                                 .getLogger(EventStatisticsServiceTest.class);
    
    
    
    @Test
    public void testQuerySeverity() {
    
    
        EsperQueryTester
                .newTest("SEVERITY_QUERY")
                .withQuery(
                        "SELECT COUNT(*) as res FROM Event(eventType.severity=Severity.MAJOR).win:time(24 hour)")
                .sendEvent(new JenkinsEventFactory().sendBuildComplete("bla", 12, "truc"))
                .expectRows(1).hasSingleResult("res", 0L).runTest();
        EsperQueryTester
                .newTest("SEVERITY_QUERY")
                .withQuery(
                        "SELECT COUNT(*) as res FROM Event(eventType.severity=Severity.INFO).win:time(24 hour)")
                .sendEvent(new JenkinsEventFactory().sendBuildComplete("bla", 12, "truc"))
                .expectRows(1).hasSingleResult("res", 1L).runTest();
        EsperQueryTester
                .newTest("SEVERITY_QUERY")
                .withQuery(
                        "SELECT COUNT(*) as res FROM Event(eventType.severity=Severity.MINOR).win:time(24 hour)")
                .sendEvent(new JenkinsEventFactory().sendBuildComplete("bla", 12, "truc"))
                .expectRows(1).hasSingleResult("res", 0L).runTest();
        EsperQueryTester
                .newTest("SEVERITY_QUERY")
                .withQuery(
                        "SELECT COUNT(*) as res FROM Event(eventType.severity=Severity.CRITICAL).win:time(24 hour)")
                .sendEvent(new JenkinsEventFactory().sendBuildComplete("bla", 12, "truc"))
                .expectRows(1).hasSingleResult("res", 0L).runTest();
        EsperQueryTester
                .newTest("SEVERITY_QUERY")
                .withQuery(
                        "SELECT COUNT(*) as res FROM Event(eventType.severity=Severity.BLOCKER).win:time(24 hour)")
                .sendEvent(new JenkinsEventFactory().sendBuildComplete("bla", 12, "truc"))
                .expectRows(1).hasSingleResult("res", 0L).runTest();
        ;
    }
}
