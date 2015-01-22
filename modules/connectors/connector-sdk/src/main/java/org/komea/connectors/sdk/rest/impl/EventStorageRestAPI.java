/**
 *
 */
package org.komea.connectors.sdk.rest.impl;

import java.io.IOException;
import java.net.ConnectException;
import java.rmi.ServerException;
import org.komea.event.model.KomeaEvent;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventStorage;

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
        eventoryClientAPI = _eventoryClientAPI;
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
            eventoryClientAPI.delete("/storage/clear/", _eventType);
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
        //

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
            eventoryClientAPI.post("/storage/push_flat", _event);
        } catch (ConnectException | ServerException e) {
            throw new RestClientException(e.getMessage(), e);
        }

    }

    @Override
    public IEventDB getEventDB(final String eventType) {
        return eventoryClientAPI.getEventStorage().getEventDB(eventType);
    }

}
