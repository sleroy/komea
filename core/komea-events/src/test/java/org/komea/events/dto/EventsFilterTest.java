package org.komea.events.dto;

import java.util.Date;
import org.junit.Test;
import org.komea.events.queries.predicates.PredicateDto;

public class EventsFilterTest {

    @Test
    public void test() {
        final String eventType = "bugs";
        final DateInterval interval = new DateInterval(new Date(), new Date());
        EventsFilter filter = new EventsFilter();
        filter.setEventType(eventType);
        filter = new EventsFilter(eventType, interval);
        filter.setInterval(filter.getInterval());
        filter.setInterval(interval.getFrom(), interval.getTo());
        filter.setWhere(new PredicateDto());
    }
}
