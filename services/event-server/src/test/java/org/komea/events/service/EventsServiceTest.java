package org.komea.events.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.events.dao.IEventsDao;
import org.komea.events.dto.DateInterval;
import org.komea.events.dto.KomeaEvent;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EventsServiceTest {

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
    private IEventsDao dao;

    private EventsService service;

    @Before
    public void setUp() {
        Mockito.when(dao.getEventTypes()).
                thenReturn(new ArrayList<>(events.keySet()));
        Mockito.when(dao.countEventsOfType(EVENT_TYPE_1)).
                thenReturn((long) events.get(EVENT_TYPE_1).size());
        Mockito.when(dao.countEventsOfType(EVENT_TYPE_2)).
                thenReturn((long) events.get(EVENT_TYPE_2).size());
        service = new EventsService();
        service.setEventsDao(dao);
        service.setValidator(new EventStorageValidatorService());
    }

    /**
     * Test of clearEventsOfType method, of class EventsService.
     */
    @Test
    public void testClearEventsOfType() {
        service.clearEventsOfType(EVENT_TYPE_1);
        Mockito.verify(dao, Mockito.times(1)).clearEventsOfType(EVENT_TYPE_1);
    }

    /**
     * Test of countAllEvents method, of class EventsService.
     */
    @Test
    public void testCountAllEvents() {
        long eventsCount = service.countAllEvents();
        assertEquals(9, eventsCount);
    }

    /**
     * Test of countEventsOfType method, of class EventsService.
     */
    @Test
    public void testCountEventsOfType() {
        service.countEventsOfType(EVENT_TYPE_1);
        Mockito.verify(dao, Mockito.times(1)).countEventsOfType(EVENT_TYPE_1);
    }

    /**
     * Test of declareEventType method, of class EventsService.
     */
    @Test
    public void testDeclareEventType() {
        service.declareEventType(EVENT_TYPE_1);
        Mockito.verify(dao, Mockito.times(1)).declareEventType(EVENT_TYPE_1);
    }

    /**
     * Test of storeEvent method, of class EventsService.
     */
    @Test
    public void testStoreEvent() {
        final KomeaEvent event = new KomeaEvent("jenkins", EVENT_TYPE_1);
        service.storeEvent(event);
        Mockito.verify(dao, Mockito.times(1)).declareEventType(EVENT_TYPE_1);
        Mockito.verify(dao, Mockito.times(1)).putEvent(event);
    }

    /**
     * Test of storeEvent method, of class EventsService.
     */
    @Test
    public void testStoreEventInvalid() {
        final KomeaEvent event = new KomeaEvent();
        service.storeEvent(event);
        Mockito.verify(dao, Mockito.times(0)).declareEventType(EVENT_TYPE_1);
        Mockito.verify(dao, Mockito.times(0)).putEvent(event);
    }

    /**
     * Test of getAllEventsOnPeriod method, of class EventsService.
     */
    @Test
    public void testGetAllEventsOnPeriod() {
        final DateInterval interval = new DateInterval(new Date(), new Date());
        service.getAllEventsOnPeriod(interval, 1000);
        Mockito.verify(dao, Mockito.times(1)).getAllEventsOnPeriod(interval, 1000);
    }

    /**
     * Test of loadEventsOfType method, of class EventsService.
     */
    @Test
    public void testLoadEventsOfType() {
        service.loadEventsOfType(EVENT_TYPE_1);
        Mockito.verify(dao, Mockito.times(1)).loadEventsOfType(EVENT_TYPE_1);
    }

    /**
     * Test of loadEventsOfTypeOnPeriod method, of class EventsService.
     */
    @Test
    public void testLoadEventsOfTypeOnPeriod() {
        final DateInterval period = new DateInterval(new Date(), new Date());
        service.loadEventsOfTypeOnPeriod(EVENT_TYPE_1, period);
        Mockito.verify(dao, Mockito.times(1)).loadEventsOfTypeOnPeriod(EVENT_TYPE_1, period);
    }

    /**
     * Test of getEventTypes method, of class EventsService.
     */
    @Test
    public void testGetEventTypes() {
        service.getEventTypes();
        Mockito.verify(dao, Mockito.times(1)).getEventTypes();
    }

    /**
     * Test of clearAllEvents method, of class EventsService.
     */
    @Test
    public void testClearAllEvents() {
        service.clearAllEvents();
        Mockito.verify(dao, Mockito.times(1)).clearEventsOfType(EVENT_TYPE_1);
        Mockito.verify(dao, Mockito.times(1)).clearEventsOfType(EVENT_TYPE_2);
    }

}
