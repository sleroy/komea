package org.komea.event.query.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.komea.event.query.IEventQueryManager;
import org.komea.event.storage.impl.EventStorage;
import org.komea.orientdb.session.document.IODocument;
import org.komea.orientdb.session.document.IODocumentToolbox;
import org.komea.orientdb.session.document.impl.OrientDocumentToolbox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orientdb.orm.session.IOrientSessionFactory;
import org.springframework.orientdb.session.impl.DatabaseConfiguration;
import org.springframework.orientdb.session.impl.OrientSessionFactory;

import com.orientechnologies.orient.core.iterator.ORecordIteratorClass;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class EventQueryManager implements IEventQueryManager {
	private final IOrientSessionFactory	documentDatabase;

	private static final Logger	        LOGGER	= LoggerFactory.getLogger(EventStorage.class);

	private final IODocumentToolbox	    oDocumentToolbox;

	public EventQueryManager(final DatabaseConfiguration _configuration) {
		this(new OrientSessionFactory(_configuration));
		// Lazy init the database session
		this.documentDatabase.getOrCreateDatabaseSession();
	}

	public EventQueryManager(final IOrientSessionFactory _factory) {
		super();
		this.documentDatabase = _factory;
		this.oDocumentToolbox = new OrientDocumentToolbox(this.documentDatabase);

	}

	@Override
	public ORecordIteratorClass<ODocument> browseEvents(final String _eventType) {
		final ORecordIteratorClass<ODocument> browseClass = this.documentDatabase.db().browseClass(_eventType);
		return browseClass;
	}

	@Override
	public void close() throws IOException {
		LOGGER.debug("Closing event query manager");
		if (this.documentDatabase != null) {
			this.documentDatabase.close();
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
			return this.documentDatabase.db().countClass(_eventType);
		} catch (final IllegalArgumentException e) {
			LOGGER.debug(e.getMessage(), e);
		}
		return 0L;
	}

	@Override
	public long countEventsPerQuery(final String _eventType, final String _whereFilter) {

		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT COUNT(*) as value FROM ");
		stringBuilder.append(_eventType);
		stringBuilder.append(" WHERE ");
		stringBuilder.append(_whereFilter);

		final List<ODocument> rawQuery = this.rawQuery(stringBuilder.toString());
		if (1 == rawQuery.size()) { return Long.class.cast(rawQuery.get(0).field("value")); }
		return 0L;
	}

	@Override
	public Iterator<IODocument> query(final String _query) {
		return this.oDocumentToolbox.query(_query);
	}

	@Override
	public List<ODocument> rawQuery(final String _query) {
		return this.oDocumentToolbox.rawQuery(_query);
	}
}