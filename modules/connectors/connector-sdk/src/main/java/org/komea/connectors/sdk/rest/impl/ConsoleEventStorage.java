/**
 *
 */
package org.komea.connectors.sdk.rest.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.komea.event.model.beans.AbstractEvent;
import org.komea.event.model.beans.BasicEvent;
import org.komea.event.model.beans.ComplexEvent;
import org.komea.event.model.beans.FlatEvent;
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

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConsoleEventStorage.class);

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
	 * @see
	 * org.komea.event.storage.IEventStorage#storeBasicEvent(org.komea.event
	 * .model.beans.BasicEvent)
	 */
	@Override
	public void storeBasicEvent(final BasicEvent _event) {
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
	 * @see
	 * org.komea.event.storage.IEventStorage#storeComplexEvent(org.komea.event
	 * .model.beans.ComplexEvent)
	 */
	@Override
	public void storeComplexEvent(final ComplexEvent _event) {
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
	 * @see
	 * org.komea.event.storage.IEventStorage#storeEvent(org.komea.event.model
	 * .beans.AbstractEvent)
	 */
	@Override
	public void storeEvent(final AbstractEvent _event) {
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
	 * @see
	 * org.komea.event.storage.IEventStorage#storeFlatEvent(org.komea.event.
	 * model.beans.FlatEvent)
	 */
	@Override
	public void storeFlatEvent(final FlatEvent _event) {
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
	 * @see org.komea.event.storage.IEventStorage#storeMap(java.util.Map)
	 */
	@Override
	public void storeMap(final Map<String, Serializable> _fieldMap) {
		try {
			LOGGER.info("POST>>> Store event {}",
					new ObjectMapper().writeValueAsString(_fieldMap));
		} catch (final JsonProcessingException e) {

			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.komea.event.storage.IEventStorage#storePojo(java.lang.Object)
	 */
	@Override
	public void storePojo(final Object _pojo) {
		try {
			LOGGER.info("POST>>> Store event {}",
					new ObjectMapper().writeValueAsString(_pojo));
		} catch (final JsonProcessingException e) {

			e.printStackTrace();
		}

	}

}
