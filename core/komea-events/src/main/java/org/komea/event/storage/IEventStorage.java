package org.komea.event.storage;

import java.io.Closeable;
import java.util.List;
import java.util.Map;
import org.komea.event.model.impl.DateInterval;
import org.komea.event.model.impl.KomeaEvent;
import org.komea.event.queries.executor.EventsFilter;
import org.komea.event.queries.executor.EventsQuery;
import org.skife.jdbi.v2.ResultIterator;

public interface IEventStorage extends Closeable {

    void clearAllEvents();

    /**
     * Clear of all the events of the type
     *
     * @param _eventType the event type;
     */
    void clearEventsOfType(String _eventType);

    long countAllEvents();

    long countEventsOfType(String eventType);

    /**
     * Declares a new event type.
     *
     * @param type the new event type.
     */
    void declareEventType(String type);

    Map<String, Number> executeQuery(EventsQuery eventsQuery);

    /**
     * Tests if a storage exist for the event type.
     *
     * @param _eventType
     * @return
     */
    boolean existStorage(String _eventType);

    List<KomeaEvent> getAllEventsOnPeriod(DateInterval period, int limit);

    List<KomeaEvent> getEventsByFilter(EventsFilter filter);

    ResultIterator<KomeaEvent> loadEventsOfType(String eventType);

    ResultIterator<KomeaEvent> loadEventsOfTypeOnPeriod(String eventType,
            DateInterval interval, int limit);

    List<String> getEventTypes();

    /**
     * Stores a event with a flattened structure.
     *
     * @param _event the event.
     */
    void storeEvent(KomeaEvent _event);

    /**
     * Converts and store a pojo as a event.
     *
     * @param _object the object
     */
    void storeEvent(Object _object);

    IEventDB getEventDB(String eventType);
}
