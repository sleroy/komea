package org.komea.events.rest;

import java.util.Date;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.events.dto.DateInterval;
import org.komea.events.dto.EventsFilter;
import org.komea.events.dto.EventsQuery;
import org.komea.events.dto.KomeaEvent;
import org.komea.events.queries.formulas.FormulaDto;
import org.komea.events.queries.formulas.FormulaType;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConsoleEventsClientTest {

    private ConsoleEventsClient client;

    @Before
    public void before() {
        client = new ConsoleEventsClient();
    }

    @Test
    public void testClearAllEvents() {
        client.clearAllEvents();
    }

    @Test
    public void testClearEventsOfType() {
        client.clearEventsOfType("toto");
    }

    @Test
    public void testCountAllEvents() {
        client.countAllEvents();
    }

    @Test
    public void testCountEventsOfType() {
        client.countEventsOfType("toto");
    }

    @Test
    public void testDeclareEventType() {
        client.declareEventType("toto");
    }

    @Test
    public void testExecuteQuery() {
        final String eventType = "new_commit";
        final String groupBy = "provider";
        final EventsFilter filter = new EventsFilter(eventType,
                new DateInterval(DateTime.now().minusDays(1).toDate(), DateTime.now().toDate()));
        final EventsQuery eventsQuery = new EventsQuery(filter, groupBy, new FormulaDto(FormulaType.COUNT));
        client.executeQuery(eventsQuery);
    }

    @Test
    public void testGetEventTypes() {
        client.getEventTypes();
    }

    @Test
    public void testGetEventsByFilter() {
        final EventsFilter filter = new EventsFilter("toto");
        client.getEventsByFilter(filter);
    }

    @Test
    public void testGetEventsOfType() {
        client.getEventsOfType("toto");
    }

    @Test
    public void testGetEventsOfTypeOnPeriod() {
        final DateInterval period = new DateInterval(new Date(), new Date());
        client.getEventsOfTypeOnPeriod("toto", period);
    }

    @Test
    public void testPushEvent() {
        final KomeaEvent event = new KomeaEvent("jenkins", "toto", new Date());
        client.pushEvent(event);
    }

    @Test
    public void testTestConnection() {
        client.testConnection();
    }
}
