/**
 *
 */
package org.komea.connectors.sdk.rest.impl;

import java.io.IOException;
import java.net.ConnectException;
import java.rmi.ServerException;

import org.komea.event.model.impl.KomeaEvent;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventStorage;

/**
 * @author sleroy
 *
 */
public class EventStorageRestAPI implements IEventStorage {

	private final EventoryClientAPI	eventoryClientAPI;

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
}
