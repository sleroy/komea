package org.komea.event.query.service;

import java.io.IOException;

import org.komea.event.query.api.IEventQueryManager;
import org.komea.event.storage.service.EventStorageService;
import org.komea.orientdb.session.IDocumentSessionFactory;
import org.komea.orientdb.session.impl.DatabaseConfiguration;
import org.komea.orientdb.session.impl.OrientDocumentDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventQueryManagerService implements IEventQueryManager {
	private final IDocumentSessionFactory documentDatabaseFactory;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EventStorageService.class);

	/**
	 *
	 */
	public EventQueryManagerService(final DatabaseConfiguration _configuration) {
		this(new OrientDocumentDatabaseFactory(_configuration));
	}

	/**
	 *
	 */
	public EventQueryManagerService(final IDocumentSessionFactory _factory) {
		super();
		this.documentDatabaseFactory = _factory;

	}

	@Override
	public void close() throws IOException {
		LOGGER.debug("Closing event query manager");
		if (this.documentDatabaseFactory != null) {
			this.documentDatabaseFactory.close();
		}

	}

	/**
	 * Returns the number of events of the type provided in parameter
	 *
	 * @param _eventType
	 *            the event typê
	 * @return the number of events
	 */
	@Override
	public long countEventsOfType(final String _eventType) {
		return this.documentDatabaseFactory.getOrCreateDatabaseSession()
				.countClass(_eventType);
	}
}