/**
 * 
 */

package org.komea.product.backend.service.esper;



import java.util.Collections;

import org.junit.Test;
import org.komea.event.factory.JenkinsEventFactory;
import org.komea.product.backend.esper.test.EsperQueryTester;
import org.komea.product.database.alert.Event;
import org.komea.product.database.alert.EventBuilder;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.enums.RetentionPeriod;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;



/**
 * @author sleroy
 */
public class RetentionQueryBuilderTest
{
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.esper.RetentionQueryBuilder#build()}.
     */
    @Test
    public final void testBuild() {
    
    
        final RetentionQueryBuilder retentionQueryBuilder =
                new RetentionQueryBuilder(Severity.BLOCKER, RetentionPeriod.ONE_MONTH);
        final String esperBlocking = retentionQueryBuilder.build();
        final EventSimpleDto buildComplete =
                new JenkinsEventFactory().sendBuildComplete("PROJECT", 1.0d, "SEVERITY");
        final EventType eventType = new EventType();
        eventType.setSeverity(Severity.BLOCKER);
        eventType.setDescription("bla");
        eventType.setName("Resource");
        final Event blockingEvent = EventBuilder.newAlert().eventType(eventType).build();
        
        EsperQueryTester.newTest("ESPER_BLOCKER").withQuery(esperBlocking).sendEvent(buildComplete)
                .dump().hasEventList().send(blockingEvent).expectRows(1)
                .matchList(Collections.singletonList(blockingEvent)).runTest();
        
        
    }
}
