package org.komea.event.storage.impl;

import java.io.IOException;

import org.komea.event.model.impl.KomeaEvent;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventDBFactory;
import org.komea.event.storage.IEventStorage;
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
}
