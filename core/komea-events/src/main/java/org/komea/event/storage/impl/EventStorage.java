package org.komea.event.storage.impl;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.komea.event.model.impl.DateInterval;
import org.komea.event.model.impl.KomeaEvent;
import org.komea.event.queries.executor.EventsFilter;
import org.komea.event.queries.executor.EventsQuery;
import org.komea.event.queries.executor.EventsQueryExecutor;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventDBFactory;
import org.komea.event.storage.IEventStorage;
import org.skife.jdbi.v2.ResultIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements the service to store events into the OrientDB database.
 * .
 *
 * @author sleroy
 */
public class EventStorage implements IEventStorage {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(EventStorage.class);

    private final EventStorageValidatorService validator = new EventStorageValidatorService();

    private final IEventDBFactory eventDBFactory;

    public EventStorage(final IEventDBFactory _eventDBFactory) {
        this.eventDBFactory = _eventDBFactory;
    }

    @Override
    public void clearEventsOfType(final String _eventType) {
        final IEventDB storage = this.eventDBFactory.getEventDB(_eventType);
        storage.removeAll();
    }

    @Override
    public void close() throws IOException {
        LOGGER.info("Closing the event storage and its database connection.");
        this.eventDBFactory.close();

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.komea.event.storage.IEventStorage#declareEventType(java.lang.String)
     */
    @Override
    public void declareEventType(final String _type) {
        this.eventDBFactory.declareEventType(_type);

    }

    /*
     * (non-Javadoc)
     *
     * @see org.komea.event.storage.IEventStorage#existStorage(java.lang.String)
     */
    @Override
    public boolean existStorage(final String _eventType) {

        return this.eventDBFactory.getEventDB(_eventType).existStorage();
    }

    @Override
    public IEventDB getEventDB(final String eventType) {
        return this.eventDBFactory.getEventDB(eventType);
    }

    @Override
    public void storeEvent(final KomeaEvent _event) {
        LOGGER.info("storeEvent " + _event);
        this.save(_event);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.komea.event.storage.IEventStorage#storeEvent(java.lang.Object)
     */
    @Override
    public void storeEvent(final Object _object) {
        this.storeEvent(new KomeaEvent(_object));

    }

    private void save(final KomeaEvent _document) {
        if (!this.validator.validate(_document)) {
            LOGGER.error("Event has been rejected {}", _document);
        } else {
            final String eventType = _document.getEventType();
            this.eventDBFactory.declareEventType(eventType);
            final IEventDB storage = this.eventDBFactory.getEventDB(eventType);
            storage.put(_document);
        }
    }

    @Override
    public void clearAllEvents() {
        for (final String eventType : getEventTypes()) {
            clearEventsOfType(eventType);
        }
    }

    @Override
    public long countAllEvents() {
        int count = 0;
        for (final String eventType : getEventTypes()) {
            count += countEventsOfType(eventType);
        }
        return count;
    }

    @Override
    public long countEventsOfType(final String eventType) {
        return this.eventDBFactory.getEventDB(eventType).count();
    }

    @Override
    public Map<String, Number> executeQuery(final EventsQuery eventsQuery) {
        return new EventsQueryExecutor(this, eventsQuery).execute();
    }

    @Override
    public List<KomeaEvent> getAllEventsOnPeriod(final DateInterval period, final int limit) {
        final List<KomeaEvent> events = new ArrayList<>(limit);
        for (final String eventType : getEventTypes()) {
            final List<KomeaEvent> subList = Lists.newArrayList(this.loadEventsOfTypeOnPeriod(eventType, period));
            events.addAll(subList);
            Collections.sort(events);
            if (events.size() > limit) {
                events.removeAll(events.subList(limit, events.size()));
            }
        }
        return events;
    }

    @Override
    public List<KomeaEvent> getEventsByFilter(final EventsFilter filter) {
        return EventsQueryExecutor.filterEvents(filter, this);
    }

    @Override
    public ResultIterator<KomeaEvent> loadEventsOfType(final String eventType) {
        return this.eventDBFactory.getEventDB(eventType).loadAll();
    }

    @Override
    public ResultIterator<KomeaEvent> loadEventsOfTypeOnPeriod(final String eventType, final DateInterval interval) {
        return this.eventDBFactory.getEventDB(eventType).loadOnPeriod(interval);
    }

    @Override
    public List<String> getEventTypes() {
        return this.eventDBFactory.getEventTypes();
    }
}
