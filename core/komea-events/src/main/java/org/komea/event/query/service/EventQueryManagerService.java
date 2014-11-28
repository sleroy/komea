package org.komea.event.query.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.komea.event.query.api.IEventQueryManager;
import org.komea.event.storage.service.EventStorageService;
import org.komea.orientdb.session.IDocumentSessionFactory;
import org.komea.orientdb.session.document.IODocument;
import org.komea.orientdb.session.impl.DatabaseConfiguration;
import org.komea.orientdb.session.impl.OrientDocumentDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orientechnologies.orient.core.iterator.ORecordIteratorClass;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class EventQueryManagerService implements IEventQueryManager {
	private final IDocumentSessionFactory documentDatabaseFactory;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EventStorageService.class);

	public EventQueryManagerService(final DatabaseConfiguration _configuration) {
		this(new OrientDocumentDatabaseFactory(_configuration));
	}

	public EventQueryManagerService(final IDocumentSessionFactory _factory) {
		super();
		this.documentDatabaseFactory = _factory;

	}

	@Override
	public ORecordIteratorClass<ODocument> browseEvents(final String _eventType) {
		final ORecordIteratorClass<ODocument> browseClass = this.documentDatabaseFactory
				.browseClass(_eventType);
		return browseClass;
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
		try {
			return this.documentDatabaseFactory.getOrCreateDatabaseSession()
					.countClass(_eventType);
		} catch (final IllegalArgumentException e) {
			LOGGER.debug(e.getMessage(), e);
		}
		return 0L;
	}

	@Override
	public long countEventsPerQuery(final String _eventType,
			final String _whereFilter) {

		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT COUNT(*) as value FROM ");
		stringBuilder.append(_eventType);
		stringBuilder.append(" WHERE ");
		stringBuilder.append(_whereFilter);

		final List<ODocument> rawQuery = this
				.rawQuery(stringBuilder.toString());
		if (1 == rawQuery.size()) {
			return Long.class.cast(rawQuery.get(0).field("value"));
		}
		return 0L;
	}

	@Override
	public Iterator<IODocument> query(final String _query) {
		return this.documentDatabaseFactory.query(_query);
	}

	@Override
	public List<ODocument> rawQuery(final String _query) {
		return this.documentDatabaseFactory.rawQuery(_query);
	}
}