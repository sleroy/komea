package org.komea.events.rest;

import java.util.Date;
import java.util.Map;
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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class EventsRestClientTest {

    @Mock
    private RestTemplate restTemplate;

    private final String host = "http://localhost:8081";
    private EventsRestClient client;

    @Before
    public void before() {
        client = new EventsRestClient(host);
        client.setRestTemplate(restTemplate);
    }

    @Test
    public void testClearAllEvents() {
        client.clearAllEvents();

        Mockito.verify(restTemplate, Mockito.times(1)).
                delete(host + "/events/clearAllEvents");
    }

    @Test
    public void testClearEventsOfType() {
        client.clearEventsOfType("toto");

        Mockito.verify(restTemplate, Mockito.times(1)).
                delete(host + "/events/clearEventsOfType/toto");
    }

    @Test
    public void testCountAllEvents() {
        Mockito.when(restTemplate.getForObject(host + "/events/countAllEvents",
                Long.class)).thenReturn(0L);
        client.countAllEvents();

        Mockito.verify(restTemplate, Mockito.times(1)).
                getForObject(host + "/events/countAllEvents", Long.class);
    }

    @Test
    public void testCountEventsOfType() {
        Mockito.when(restTemplate.getForObject(host + "/events/countEventsOfType/toto",
                Long.class)).thenReturn(0L);
        client.countEventsOfType("toto");

        Mockito.verify(restTemplate, Mockito.times(1)).
                getForObject(host + "/events/countEventsOfType/toto", Long.class);
    }

    @Test
    public void testDeclareEventType() {
        client.declareEventType("toto");

        Mockito.verify(restTemplate, Mockito.times(1)).
                postForObject(host + "/events/declareEventType", "toto", Void.class);
    }

    @Test
    public void testExecuteQuery() {
        final String eventType = "new_commit";
        final String groupBy = "provider";
        final EventsFilter filter = new EventsFilter(eventType,
                new DateInterval(DateTime.now().minusDays(1).toDate(), DateTime.now().toDate()));
        final EventsQuery eventsQuery = new EventsQuery(filter, groupBy, new FormulaDto(FormulaType.COUNT));
        client.executeQuery(eventsQuery);

        Mockito.verify(restTemplate, Mockito.times(1)).
                postForObject(host + "/events/executeQuery", eventsQuery, Map.class);
    }

    @Test
    public void testGetEventTypes() {
        Mockito.when(restTemplate.getForObject(host + "/events/getEventTypes", String[].class))
                .thenReturn(new String[0]);
        client.getEventTypes();

        Mockito.verify(restTemplate, Mockito.times(1)).
                getForObject(host + "/events/getEventTypes", String[].class);
    }

    @Test
    public void testGetEventsByFilter() {
        final EventsFilter filter = new EventsFilter("toto");
        Mockito.when(restTemplate.postForObject(host + "/events/getEventsByFilter", filter, KomeaEvent[].class))
                .thenReturn(new KomeaEvent[0]);
        client.getEventsByFilter(filter);

        Mockito.verify(restTemplate, Mockito.times(1)).
                postForObject(host + "/events/getEventsByFilter", filter, KomeaEvent[].class);
    }

    @Test
    public void testGetEventsOfType() {
        Mockito.when(restTemplate.getForObject(host + "/events/getEventsOfType/toto", KomeaEvent[].class))
                .thenReturn(new KomeaEvent[0]);
        client.getEventsOfType("toto");

        Mockito.verify(restTemplate, Mockito.times(1)).
                getForObject(host + "/events/getEventsOfType/toto", KomeaEvent[].class);
    }

    @Test
    public void testGetEventsOfTypeOnPeriod() {
        final DateInterval period = new DateInterval(new Date(), new Date());
        Mockito.when(restTemplate.postForObject(host + "/events/getEventsOfTypeOnPeriod/toto", period, KomeaEvent[].class))
                .thenReturn(new KomeaEvent[0]);

        client.getEventsOfTypeOnPeriod("toto", period);

        Mockito.verify(restTemplate, Mockito.times(1)).
                postForObject(host + "/events/getEventsOfTypeOnPeriod/toto", period, KomeaEvent[].class);
    }

    @Test
    public void testPushEvent() {
        final KomeaEvent event = new KomeaEvent("jenkins", "toto", new Date());
        client.pushEvent(event);

        Mockito.verify(restTemplate, Mockito.times(1)).
                postForObject(host + "/events/pushEvent", event, Void.class);
    }

    @Test
    public void testTestConnection() {
        client.testConnection();

        Mockito.verify(restTemplate, Mockito.times(1)).
                getForObject(host + "/events/hello", String.class);
    }
}
