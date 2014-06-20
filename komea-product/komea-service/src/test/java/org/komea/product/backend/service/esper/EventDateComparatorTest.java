/**
 *
 */
package org.komea.product.backend.service.esper;

import java.util.Date;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.komea.product.database.alert.EventBuilder;

/**
 * @author sleroy
 */
public class EventDateComparatorTest {

    /**
     * Test method for
     * {@link org.komea.product.backend.service.esper.EventDateComparator#compare(org.komea.product.database.alert.IEvent, org.komea.product.database.alert.IEvent)}
     * .
     */
    @Test
    public final void testCompare() throws Exception {

        final EventDateComparator eventDateComparator = new EventDateComparator();
        final Date date = new Date();
        assertEquals(
                0,
                eventDateComparator.compare(EventBuilder.newAlert().at(date).build(), EventBuilder
                        .newAlert().at(date).build()));
        assertEquals(
                -1,
                eventDateComparator.compare(EventBuilder.newAlert().at(date).build(), EventBuilder
                        .newAlert().at(null).build()));
        assertEquals(
                1,
                eventDateComparator.compare(EventBuilder.newAlert().at(null).build(), EventBuilder
                        .newAlert().at(date).build()));
    }
}
