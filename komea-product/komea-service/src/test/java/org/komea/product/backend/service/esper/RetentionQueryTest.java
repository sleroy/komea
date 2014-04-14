/**
 * 
 */

package org.komea.product.backend.service.esper;



import org.junit.Test;
import org.komea.event.factory.JenkinsEventFactory;
import org.komea.product.backend.esper.test.CEPQueryTester;
import org.komea.product.database.enums.RetentionPeriod;
import org.komea.product.database.enums.Severity;



/**
 * @author sleroy
 */
public class RetentionQueryTest
{
    
    
    @Test ()
    public void testQueryRetention() {
    
    
        final CEPQueryTester newTest = CEPQueryTester.newTest();
        newTest.withQuery(new RetentionQuery(Severity.BLOCKER, RetentionPeriod.ONE_HOUR))
                .sendEvent(new JenkinsEventFactory().sendBuildComplete("bla", 12, "truc"), 3)
                .sendEvent(new JenkinsEventFactory().sendBuildFailed("bla", 12, "truc"), 2).dump()
                .expectStorageSize(2);
        newTest.getMockEventTypes().get("build_failed").setSeverity(Severity.BLOCKER);
        newTest.runTest();
    }
}
