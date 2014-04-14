/**
 * 
 */

package org.komea.product.backend.service.esper.stats;



import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.komea.event.factory.JenkinsEventFactory;
import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.product.backend.esper.test.CEPQueryTester;
import org.komea.product.database.dto.EventSimpleDto;
import org.slf4j.LoggerFactory;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;



/**
 * @author sleroy
 * @version $Revision: 1.0 $
 */
@BenchmarkOptions(warmupRounds = 5, benchmarkRounds = 10)
public class EventStatisticsServicePerformanceTest
{
    
    
    private static final org.slf4j.Logger LOGGER       =
                                                               LoggerFactory
                                                                       .getLogger(EventStatisticsServicePerformanceTest.class);
    
    
    @Rule
    public TestRule                       benchmarkRun = new BenchmarkRule();
    
    
    
    /**
     * Tests the query.
     */
    @Test 
    public void testBuildProviderEventFrequencyQuery() {
    
    
        final ICEPQueryImplementation buildProviderEventFrequencyQuery =
                new EventStatisticsService().buildProviderEventFrequencyQuery();
        
        
        final EventSimpleDto sendBuildComplete =
                new JenkinsEventFactory().sendBuildComplete("bla", 12, "truc");
        CEPQueryTester.newTest().withQuery(buildProviderEventFrequencyQuery)
                .sendEvent(sendBuildComplete, 40000).runTest();
    }
    
    
    @Test 
    public void testQuerySeverity() {
    
    
        //
        //
        // CEPQueryTester
        // .newTest("SEVERITY_QUERY")
        // .withQuery(
        // "SELECT COUNT(*) as res FROM Event(eventType.severity=Severity.MAJOR).win:time(24 hour)")
        // .sendEvent(new JenkinsEventFactory().sendBuildComplete("bla", 12, "truc"))
        // .expectRows(1).hasSingleResult("res", 0L).runTest();
        // CEPQueryTester
        // .newTest("SEVERITY_QUERY")
        // .withQuery(
        // "SELECT COUNT(*) as res FROM Event(eventType.severity=Severity.INFO).win:time(24 hour)")
        // .sendEvent(new JenkinsEventFactory().sendBuildComplete("bla", 12, "truc"))
        // .expectRows(1).hasSingleResult("res", 1L).runTest();
        // CEPQueryTester
        // .newTest("SEVERITY_QUERY")
        // .withQuery(
        // "SELECT COUNT(*) as res FROM Event(eventType.severity=Severity.MINOR).win:time(24 hour)")
        // .sendEvent(new JenkinsEventFactory().sendBuildComplete("bla", 12, "truc"))
        // .expectRows(1).hasSingleResult("res", 0L).runTest();
        // CEPQueryTester
        // .newTest("SEVERITY_QUERY")
        // .withQuery(
        // "SELECT COUNT(*) as res FROM Event(eventType.severity=Severity.CRITICAL).win:time(24 hour)")
        // .sendEvent(new JenkinsEventFactory().sendBuildComplete("bla", 12, "truc"))
        // .expectRows(1).hasSingleResult("res", 0L).runTest();
        // CEPQueryTester
        // .newTest("SEVERITY_QUERY")
        // .withQuery(
        // "SELECT COUNT(*) as res FROM Event(eventType.severity=Severity.BLOCKER).win:time(24 hour)")
        // .sendEvent(new JenkinsEventFactory().sendBuildComplete("bla", 12, "truc"))
        // .expectRows(1).hasSingleResult("res", 0L).runTest();
        // ;
    }
}
