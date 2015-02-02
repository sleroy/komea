/**
 *
 */
package org.komea.connectors.sdk.rest.impl;

import java.io.IOException;
import java.net.ConnectException;
import java.rmi.ServerException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.komea.event.model.impl.DateInterval;
import org.komea.event.model.impl.KomeaEvent;
import org.komea.event.queries.executor.EventsFilter;
import org.komea.event.queries.executor.EventsQuery;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventStorage;
import org.skife.jdbi.v2.ResultIterator;

/**
 * @author sleroy
 *
 */
public class EventStorageRestAPI implements IEventStorage {

    private final EventoryClientAPI eventoryClientAPI;

    /**
     * @param _eventoryClientAPI
     */
    public EventStorageRestAPI(final EventoryClientAPI _eventoryClientAPI) {
        this.eventoryClientAPI = _eventoryClientAPI;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.komea.event.storage.IEventStorage#clearEventsOfType(java.lang.String)
     */
    @Override
    public void clearEventsOfType(final String _eventType) {

        try {
            this.eventoryClientAPI.deleteURL("storage", _eventType, "clear");
        } catch (ConnectException | ServerException e) {
            throw new RestClientException(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see java.io.Closeable#close()
     */
    @Override
    public void close() throws IOException {
        //

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.komea.event.storage.IEventStorage#declareEventType(java.lang.String)
     */
    @Override
    public void declareEventType(final String _type) {
        try {
            this.eventoryClientAPI.postURL("storage", _type, "declare");
        } catch (ConnectException | ServerException e) {
            throw new RestClientException(e.getMessage(), e);
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see org.komea.event.storage.IEventStorage#existStorage(java.lang.String)
     */
    @Override
    public boolean existStorage(final String _eventType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IEventDB getEventDB(final String eventType) {
        throw new UnsupportedOperationException();
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
            this.eventoryClientAPI.postURL(_event, "storage", "push");
        } catch (ConnectException | ServerException e) {
            throw new RestClientException(e.getMessage(), e);
        }

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

    @Override
    public void clearAllEvents() {
        try {
            this.eventoryClientAPI.deleteURL("storage", "clearAllEvents");
        } catch (ConnectException | ServerException e) {
            throw new RestClientException(e.getMessage(), e);
        }
    }

    @Override
    public long countAllEvents() {
        try {
            return this.eventoryClientAPI.get(Long.class, "storage", "countAllEvents");
        } catch (ConnectException | ServerException e) {
            throw new RestClientException(e.getMessage(), e);
        }
    }

    @Override
    public long countEventsOfType(final String eventType) {
        try {
            return this.eventoryClientAPI.get(Long.class, "storage", eventType, "count");
        } catch (ConnectException | ServerException e) {
            throw new RestClientException(e.getMessage(), e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Number> executeQuery(final EventsQuery eventsQuery) {
        try {
            return this.eventoryClientAPI.post(eventsQuery, Map.class, "storage", "executeQuery");
        } catch (ConnectException | ServerException e) {
            throw new RestClientException(e.getMessage(), e);
        }
    }

    @Override
    public List<KomeaEvent> getAllEventsOnPeriod(final DateInterval period,
            final int limit) {
        try {
            return Arrays.asList(this.eventoryClientAPI.post(
                    period, KomeaEvent[].class, "storage", "getAllEventsOnPeriod", String.valueOf(limit)));
        } catch (ConnectException | ServerException e) {
            throw new RestClientException(e.getMessage(), e);
        }
    }

    @Override
    public List<KomeaEvent> getEventsByFilter(final EventsFilter filter) {
        try {
            return Arrays.asList(this.eventoryClientAPI.post(
                    filter, KomeaEvent[].class, "storage", "getEventsByFilter"));
        } catch (ConnectException | ServerException e) {
            throw new RestClientException(e.getMessage(), e);
        }
    }

    @Override
    public ResultIterator<KomeaEvent> loadEventsOfType(final String eventType) {
        try {
            return fromCollection(Arrays.asList(this.eventoryClientAPI.get(
                    KomeaEvent[].class, "storage", "getEventsOfType", eventType)));
        } catch (ConnectException | ServerException e) {
            throw new RestClientException(e.getMessage(), e);
        }
    }

    @Override
    public ResultIterator<KomeaEvent> loadEventsOfTypeOnPeriod(final String eventType,
            final DateInterval interval) {
        try {
            return fromCollection(Arrays.asList(this.eventoryClientAPI.post(
                    interval, KomeaEvent[].class, "storage", "getEventsOfTypeOnPeriod", eventType)));
        } catch (ConnectException | ServerException e) {
            throw new RestClientException(e.getMessage(), e);
        }
    }

    @Override
    public List<String> getEventTypes() {
        try {
            return Arrays.asList(this.eventoryClientAPI.get(String[].class, "storage", "getEventTypes"));
        } catch (ConnectException | ServerException e) {
            throw new RestClientException(e.getMessage(), e);
        }
    }

    private <T> ResultIterator<T> fromCollection(final Collection<T> col) {
        final Iterator<T> iterator = col.iterator();
        return new ResultIterator<T>() {

            @Override
            public void close() {
                // do nothing
            }

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public T next() {
                return iterator.next();
            }

            @Override
            public void remove() {
                iterator.remove();
            }
        };
    }
}
