/**
 *
 */
package org.komea.connectors.sdk.rest.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.komea.event.model.impl.DateInterval;
import org.komea.event.model.impl.KomeaEvent;
import org.komea.event.queries.executor.EventsFilter;
import org.komea.event.queries.executor.EventsQuery;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventStorage;
import org.skife.jdbi.v2.ResultIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sleroy
 *
 */
public class ConsoleEventStorage implements IEventStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleEventStorage.class);

    /*
     * (non-Javadoc)
     *
     * @see
     * org.komea.event.storage.IEventStorage#clearEventsOfType(java.lang.String)
     */
    @Override
    public void clearEventsOfType(final String _eventType) {
        LOGGER.info("REQUEST>>> Requesting to clear the event types {}",
                _eventType);

    }

    /*
     * (non-Javadoc)
     *
     * @see java.io.Closeable#close()
     */
    @Override
    public void close() throws IOException {
        LOGGER.info("Closing event storage");

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.komea.event.storage.IEventStorage#declareEventType(java.lang.String)
     */
    @Override
    public void declareEventType(final String _type) {
        LOGGER.info("REQUEST>>> Declares the event types {}", _type);

    }

    /*
     * (non-Javadoc)
     *
     * @see org.komea.event.storage.IEventStorage#existStorage(java.lang.String)
     */
    @Override
    public boolean existStorage(final String _eventType) {
        LOGGER.info("POST>>> Exist storage ?{}", _eventType);
        return false;
    }

    @Override
    public IEventDB getEventDB(final String eventType) {
        LOGGER.info("getEventDB {}", eventType);
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.komea.event.storage.IEventStorage#storeFlatEvent(org.komea.event.
     * model.beans.FlatEvent)
     */
    @Override
    public void storeEvent(final KomeaEvent _event) {
        try {
            LOGGER.info("POST>>> Store event {}",
                    new ObjectMapper().writeValueAsString(_event));
        } catch (final JsonProcessingException e) {

            e.printStackTrace();
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see org.komea.event.storage.IEventStorage#storeEvent(java.lang.Object)
     */
    @Override
    public void storeEvent(final Object _object) {
        try {
            LOGGER.info("POST>>> Store pojo {}",
                    new ObjectMapper().writeValueAsString(new KomeaEvent(_object)));
        } catch (final JsonProcessingException e) {

            e.printStackTrace();
        }

    }

    @Override
    public void clearAllEvents() {
        LOGGER.info("clearAllEvents");
    }

    @Override
    public long countAllEvents() {
        LOGGER.info("countAllEvents");
        return 0;
    }

    @Override
    public long countEventsOfType(String eventType) {
        LOGGER.info("countEventsOfType {}", eventType);
        return 0;
    }

    @Override
    public Map<String, Number> executeQuery(EventsQuery eventsQuery) {
        LOGGER.info("executeQuery {}", eventsQuery);
        return null;
    }

    @Override
    public List<KomeaEvent> getAllEventsOnPeriod(DateInterval period, int limit) {
        LOGGER.info("getAllEventsOnPeriod {} - {}", period, limit);
        return null;
    }

    @Override
    public List<KomeaEvent> getEventsByFilter(EventsFilter filter) {
        LOGGER.info("getEventsByFilter {}", filter);
        return null;
    }

    @Override
    public ResultIterator<KomeaEvent> loadEventsOfType(String eventType) {
        LOGGER.info("loadEventsOfType {}", eventType);
        return null;
    }

    @Override
    public ResultIterator<KomeaEvent> loadEventsOfTypeOnPeriod(String eventType,
            DateInterval interval, int limit) {
        LOGGER.info("loadEventsOfTypeOnPeriod {} - {} - {}", eventType, interval, limit);
        return null;
    }

    @Override
    public List<String> getEventTypes() {
        LOGGER.info("getEventTypes");
        return null;
    }
}
