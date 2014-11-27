/**
 *
 */
package org.komea.event.storage.service;

import java.io.IOException;

import org.komea.event.model.api.IBasicEvent;
import org.komea.event.model.api.IComplexEvent;
import org.komea.event.model.api.IFlatEvent;
import org.komea.event.storage.api.IEventStorage;
import org.komea.event.storage.convertor.BasicEventDocumentConvertor;
import org.komea.event.storage.convertor.ComplexEventDocumentConvertor;
import org.komea.event.storage.convertor.FlatEventDocumentConvertor;
import org.komea.orientdb.session.IDocumentSessionFactory;
import org.komea.orientdb.session.document.IODocument;
import org.komea.orientdb.session.impl.DatabaseConfiguration;
import org.komea.orientdb.session.impl.OrientDocumentDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements the service to store events into the OrientDB database.
 * .
 *
 * @author sleroy
 *
 */
public class EventStorageService implements IEventStorage {

	private final IDocumentSessionFactory documentDatabaseFactory;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EventStorageService.class);

	private final EventStorageValidatorService validator = new EventStorageValidatorService();

	/**
	 *
	 */
	public EventStorageService(final DatabaseConfiguration _configuration) {
		this(new OrientDocumentDatabaseFactory(_configuration));
	}

	/**
	 *
	 */
	public EventStorageService(final IDocumentSessionFactory _factory) {
		super();
		this.documentDatabaseFactory = _factory;

	}

	@Override
	public void close() throws IOException {
		LOGGER.debug("Closing event storage");
		if (this.documentDatabaseFactory != null) {
			this.documentDatabaseFactory.close();
		}

	}

	@Override
	public void storeComplexEvent(final IComplexEvent _event) {
		final IODocument newDocument = this.documentDatabaseFactory
				.newDocument(_event.getEventType());
		new ComplexEventDocumentConvertor(_event).convert(newDocument);
		this.save(newDocument);

	}

	@Override
	public void storeEvent(final IBasicEvent _event) {
		final IODocument newDocument = this.documentDatabaseFactory
				.newDocument(_event.getEventType());
		new BasicEventDocumentConvertor<IBasicEvent>(_event)
				.convert(newDocument);
		this.save(newDocument);

	}

	@Override
	public void storeFlatEvent(final IFlatEvent _event) {

		final IODocument document = new FlatEventDocumentConvertor(
				this.documentDatabaseFactory, _event).convert();
		this.save(document);

	}

	private void save(final IODocument _document) {
		if (!this.validator.validate(_document)) {
			LOGGER.error("Event has been rejected {}", _document.dump());
		} else {
			_document.save();
		}
	}
}
