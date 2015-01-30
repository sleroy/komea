/**
 *
 */
package org.komea.connectors.sdk.rest.impl;

import java.io.IOException;

import org.komea.event.model.impl.KomeaEvent;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author sleroy
 *
 */
public class ConsoleEventStorage implements IEventStorage {

	private static final Logger	LOGGER	= LoggerFactory.getLogger(ConsoleEventStorage.class);

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
		throw new UnsupportedOperationException("Not supported yet.");
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
}
