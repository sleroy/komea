/**
 *
 */
package org.komea.event.storage.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.komea.event.model.beans.AbstractEvent;
import org.komea.event.model.beans.BasicEvent;
import org.komea.event.model.beans.ComplexEvent;
import org.komea.event.model.beans.FlatEvent;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventStorage;
import org.komea.event.storage.IEventDBFactory;
import org.komea.event.storage.convertor.BasicEventDocumentConvertor;
import org.komea.event.storage.convertor.ComplexEventDocumentConvertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements the service to store events into the OrientDB database.
 * .
 *
 * @author sleroy
 *
 */
public class EventStorage implements IEventStorage {

	private static final String	               EVENT_KEYS	= "eventKeys";

	private static final Logger	               LOGGER	  = LoggerFactory.getLogger(EventStorage.class);

	private final EventStorageValidatorService	validator	= new EventStorageValidatorService();

	private final IEventDBFactory	           eventDBFactory;

	public EventStorage(final IEventDBFactory _eventDBFactory) {
		this.eventDBFactory = _eventDBFactory;
	}

	@Override
	public void clearEventsOfType(final String _eventType) {
		IEventDB storage = null;
		try {
			storage = this.eventDBFactory.getEventDB(_eventType);
			storage.removeAll();
		} finally {
			IOUtils.closeQuietly(storage);
		}
	}

	@Override
	public void close() throws IOException {
		LOGGER.info("Closing the event storage and its database connection.");
		this.eventDBFactory.close();

	}

	@Override
	public void storeBasicEvent(final BasicEvent _event) {
		final FlatEvent flatEvent = new FlatEvent();
		new BasicEventDocumentConvertor(_event).convert(flatEvent);
		this.save(flatEvent);

	}

	@Override
	public void storeComplexEvent(final ComplexEvent _event) {
		final FlatEvent newDocument = new FlatEvent();
		new ComplexEventDocumentConvertor(_event).convert(newDocument);
		this.save(newDocument);

	}

	@Override
	public void storeEvent(final AbstractEvent _event) {
		this.storeFlatEvent(new FlatEvent(_event));

	}

	@Override
	public void storeFlatEvent(final FlatEvent _event) {

		this.save(_event);

	}

	@Override
	public void storeMap(final Map<String, Serializable> _fieldMap) {
		this.storeFlatEvent(new FlatEvent(_fieldMap));

	}

	@Override
	public void storePojo(final Object _pojo) {
		Validate.notNull(_pojo);
		this.storeFlatEvent(new FlatEvent(_pojo));

	}

	private void save(final FlatEvent _document) {
		if (!this.validator.validate(_document)) {
			LOGGER.error("Event has been rejected {}", _document);
		} else {
			final IEventDB storage = this.eventDBFactory.getEventDB(_document.getEventType());
			storage.put(_document);
		}
	}
}
