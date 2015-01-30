package org.komea.events.rest;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.events.dto.DateInterval;
import org.komea.events.dto.EventsFilter;
import org.komea.events.dto.EventsQuery;
import org.komea.events.dto.KomeaEvent;
import org.komea.events.queries.formulas.FormulaDto;
import org.komea.events.queries.formulas.FormulaType;
import org.komea.events.service.IEventsService;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.skife.jdbi.v2.ResultIterator;

@RunWith(MockitoJUnitRunner.class)
public class EventsControllerTest {

    private static final String EVENT_TYPE_1 = "new_commit";
    private static final String EVENT_TYPE_2 = "bugs";
    private static final String AUTHOR_KEY = "author";
    private static final String AUTHOR_1 = "author_1";
    private static final String AUTHOR_2 = "author_2";
    private static final String AUTHOR_3 = "author_3";
    private static final Map<String, List<KomeaEvent>> events = Maps.newHashMap();

    @BeforeClass
    public static void beforeClass() {
        addEvent(null, EVENT_TYPE_1);
        addEvent(AUTHOR_1, EVENT_TYPE_1);
        addEvent(AUTHOR_2, EVENT_TYPE_1);
        addEvent(AUTHOR_2, EVENT_TYPE_1);
        addEvent(AUTHOR_3, EVENT_TYPE_1);
        addEvent(AUTHOR_3, EVENT_TYPE_1);
        addEvent(AUTHOR_3, EVENT_TYPE_1);
        addEvent(AUTHOR_3, EVENT_TYPE_1);
        addEvent(AUTHOR_3, EVENT_TYPE_2);
    }

    private static void addEvent(final String author, final String eventType) {
        final KomeaEvent event = new KomeaEvent("git", eventType, new Date());
        event.put(AUTHOR_KEY, author);
        if (!events.containsKey(eventType)) {
            events.put(eventType, Lists.<KomeaEvent>newArrayList());
        }
        events.get(eventType).add(event);
    }

    @Mock
    private IEventsService service;

    private EventsController controller;

    @Before
    public void before() {
        Mockito.when(service.getEventTypes()).
                thenReturn(new ArrayList<>(events.keySet()));
        Mockito.when(service.loadEventsOfType(EVENT_TYPE_1)).
                thenReturn(getResultIterator(events.get(EVENT_TYPE_1)));
        Mockito.when(service.loadEventsOfType(EVENT_TYPE_2)).
                thenReturn(getResultIterator(events.get(EVENT_TYPE_2)));
        Mockito.when(service.loadEventsOfTypeOnPeriod(
                Mockito.eq(EVENT_TYPE_1), Mockito.any(DateInterval.class))).
                thenReturn(getResultIterator(events.get(EVENT_TYPE_1)));
        controller = new EventsController();
        controller.setEventsService(service);
    }

    @Test
    public void testExecuteQuery() {

        final String groupBy = AUTHOR_KEY;
        final FormulaDto formula = new FormulaDto(FormulaType.COUNT);
        final EventsFilter filter = new EventsFilter(EVENT_TYPE_1);
        final EventsQuery query = new EventsQuery(filter, groupBy, formula);
        final Map<String, Number> map = controller.executeQuery(query);

        Mockito.verify(service, Mockito.times(1)).declareEventType(EVENT_TYPE_1);
        Assert.assertEquals(3, map.size());
        Assert.assertEquals(1, map.get(AUTHOR_1).intValue());
        Assert.assertEquals(2, map.get(AUTHOR_2).intValue());
        Assert.assertEquals(4, map.get(AUTHOR_3).intValue());
    }

    @Test
    public void testGetEventsByFilter() {
        final EventsFilter filter = new EventsFilter(EVENT_TYPE_1);
        final List<KomeaEvent> result = controller.getEventsByFilter(filter);
        Mockito.verify(service, Mockito.times(1)).declareEventType(EVENT_TYPE_1);
        Assert.assertEquals(events.get(EVENT_TYPE_1).size(), result.size());
    }

    @Test
    public void testClearEventsOfType() {
        controller.clearEventsOfType(EVENT_TYPE_1);
        Mockito.verify(service, Mockito.times(1)).declareEventType(EVENT_TYPE_1);
        Mockito.verify(service, Mockito.times(1)).clearEventsOfType(EVENT_TYPE_1);
    }

    @Test
    public void testCountEventsOfType() {
        controller.countEventsOfType(EVENT_TYPE_1);
        Mockito.verify(service, Mockito.times(1)).declareEventType(EVENT_TYPE_1);
        Mockito.verify(service, Mockito.times(1)).countEventsOfType(EVENT_TYPE_1);
    }

    @Test
    public void testClearAllEvents() {
        controller.clearAllEvents();
        Mockito.verify(service, Mockito.times(1)).clearAllEvents();
    }

    @Test
    public void testDeclareEventType() {
        controller.declareEventType(EVENT_TYPE_1);
        Mockito.verify(service, Mockito.times(1)).declareEventType(EVENT_TYPE_1);
    }

    @Test
    public void testPushEvent() {
        final KomeaEvent event = new KomeaEvent("jenkins", EVENT_TYPE_1);
        controller.pushEvent(event);
        Mockito.verify(service, Mockito.times(1)).declareEventType(EVENT_TYPE_1);
        Mockito.verify(service, Mockito.times(1)).storeEvent(event);
    }

    @Test
    public void testGetAllEventsOnPeriod() {
        final DateInterval interval = new DateInterval(new Date(), new Date());
        controller.getAllEventsOnPeriod(1000, interval);
        Mockito.verify(service, Mockito.times(1)).getAllEventsOnPeriod(interval, 1000);
    }

    @Test
    public void testGetEventTypes() {
        controller.getEventTypes();
        Mockito.verify(service, Mockito.times(1)).getEventTypes();
    }

    @Test
    public void testTestConnection() {
        final String s = controller.testConnection();
        Assert.assertNotNull(s);
        Assert.assertFalse(s.isEmpty());
    }

    @Test
    public void testGetEventsOfType() {
        controller.getEventsOfType(EVENT_TYPE_1);
        Mockito.verify(service, Mockito.times(1)).declareEventType(EVENT_TYPE_1);
        Mockito.verify(service, Mockito.times(1)).loadEventsOfType(EVENT_TYPE_1);
    }

    @Test
    public void testGetEventsOfTypeOnPeriod() {
        final DateInterval period = new DateInterval(new Date(), new Date());
        controller.getEventsOfTypeOnPeriod(EVENT_TYPE_1, period);
        Mockito.verify(service, Mockito.times(1)).declareEventType(EVENT_TYPE_1);
        Mockito.verify(service, Mockito.times(1)).loadEventsOfTypeOnPeriod(EVENT_TYPE_1, period);
    }

    private ResultIterator<KomeaEvent> getResultIterator(final List<KomeaEvent> events) {
        final Iterator<KomeaEvent> iterator = events.iterator();
        return new ResultIterator<KomeaEvent>() {

            @Override
            public void close() {
            }

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public KomeaEvent next() {
                return iterator.next();
            }

            @Override
            public void remove() {
                iterator.remove();
            }
        };
    }

}
