/**
 *
 */
package org.komea.connectors.sdk.rest.impl;

import java.io.IOException;
import java.io.Serializable;
import java.net.ConnectException;
import java.rmi.ServerException;
import java.util.Map;

import org.komea.event.model.beans.AbstractEvent;
import org.komea.event.model.beans.BasicEvent;
import org.komea.event.model.beans.ComplexEvent;
import org.komea.event.model.beans.FlatEvent;
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
	 * org.komea.event.storage.IEventStorage#storeBasicEvent(org.komea.event
	 * .model.beans.BasicEvent)
	 */
	@Override
	public void storeBasicEvent(final BasicEvent _event) {
		try {
			eventoryClientAPI.post("/storage/push", _event);
		} catch (ConnectException | ServerException e) {
			throw new RestClientException(e.getMessage(), e);
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.komea.event.storage.IEventStorage#storeComplexEvent(org.komea.event
	 * .model.beans.ComplexEvent)
	 */
	@Override
	public void storeComplexEvent(final ComplexEvent _event) {
		try {
			eventoryClientAPI.post("/storage/push_complex", _event);
		} catch (ConnectException | ServerException e) {
			throw new RestClientException(e.getMessage(), e);
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.komea.event.storage.IEventStorage#storeEvent(org.komea.event.model
	 * .beans.AbstractEvent)
	 */
	@Override
	public void storeEvent(final AbstractEvent _event) {
		storeFlatEvent(new FlatEvent(_event));

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.komea.event.storage.IEventStorage#storeFlatEvent(org.komea.event.
	 * model.beans.FlatEvent)
	 */
	@Override
	public void storeFlatEvent(final FlatEvent _event) {
		try {
			eventoryClientAPI.post("/storage/push_flat", _event);
		} catch (ConnectException | ServerException e) {
			throw new RestClientException(e.getMessage(), e);
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.komea.event.storage.IEventStorage#storeMap(java.util.Map)
	 */
	@Override
	public void storeMap(final Map<String, Serializable> _fieldMap) {
		storeFlatEvent(new FlatEvent(_fieldMap));

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.komea.event.storage.IEventStorage#storePojo(java.lang.Object)
	 */
	@Override
	public void storePojo(final Object _pojo) {
		storeFlatEvent(new FlatEvent(_pojo));

	}

}
