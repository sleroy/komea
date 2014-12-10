/**
 *
 */
package org.komea.event.storage.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.komea.event.model.IFlatEvent;
import org.komea.event.model.beans.AbstractEvent;
import org.komea.event.model.beans.BasicEvent;
import org.komea.event.model.beans.ComplexEvent;
import org.komea.event.model.beans.FlatEvent;
import org.komea.event.storage.IEventStorage;
import org.komea.event.storage.convertor.BasicEventDocumentConvertor;
import org.komea.event.storage.convertor.ComplexEventDocumentConvertor;
import org.komea.event.storage.convertor.FlatEventDocumentConvertor;
import org.komea.orientdb.session.document.IODocument;
import org.komea.orientdb.session.document.IODocumentToolbox;
import org.komea.orientdb.session.document.impl.OrientDocumentToolbox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orientdb.orm.session.IOrientSessionFactory;
import org.springframework.orientdb.session.impl.DatabaseConfiguration;
import org.springframework.orientdb.session.impl.OrientSessionFactory;

/**
 * This class implements the service to store events into the OrientDB database.
 * .
 *
 * @author sleroy
 *
 */
public class EventStorage implements IEventStorage {

	private final IOrientSessionFactory	       orientSessionFactory;

	private static final Logger	               LOGGER	  = LoggerFactory.getLogger(EventStorage.class);

	private final EventStorageValidatorService	validator	= new EventStorageValidatorService();

	private final IODocumentToolbox	           toolbox;

	public EventStorage(final DatabaseConfiguration _configuration) {
		this(new OrientSessionFactory(_configuration));
		// Lazy init the database session
		this.orientSessionFactory.getOrCreateDB();
	}

	public EventStorage(final IOrientSessionFactory _sessionFactory) {
		this(_sessionFactory, new OrientDocumentToolbox(_sessionFactory));
	}

	public EventStorage(final IOrientSessionFactory _factory, final IODocumentToolbox _toolbox) {
		super();
		this.orientSessionFactory = _factory;
		this.toolbox = _toolbox;
		new EventTypeSchemaUpdater(_factory);

	}

	@Override
	public void close() throws IOException {
		LOGGER.info("Closing the event storage and its database connection.");

		if (this.orientSessionFactory != null) {
			this.orientSessionFactory.close();
		}

	}

	@Override
	public void storeBasicEvent(final BasicEvent _event) {
		final IODocument newDocument = this.toolbox.newDocument(_event.getEventType());
		new BasicEventDocumentConvertor(_event).convert(newDocument);
		this.save(newDocument);

	}

	@Override
	public void storeComplexEvent(final ComplexEvent _event) {
		final IODocument newDocument = this.toolbox.newDocument(_event.getEventType());
		new ComplexEventDocumentConvertor(_event).convert(newDocument);
		this.save(newDocument);

	}

	@Override
	public void storeEvent(final AbstractEvent _event) {
		this.storeFlatEvent(new FlatEvent(_event));

	}

	@Override
	public void storeFlatEvent(final IFlatEvent _event) {

		final IODocument document = new FlatEventDocumentConvertor(this.toolbox, _event).convert();
		this.save(document);

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

	private void save(final IODocument _document) {
		if (!this.validator.validate(_document)) {
			LOGGER.error("Event has been rejected {}", _document.dump());
		} else {
			_document.save();
		}
	}
}
