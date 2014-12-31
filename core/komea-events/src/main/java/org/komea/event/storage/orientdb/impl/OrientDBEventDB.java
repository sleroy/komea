package org.komea.event.storage.orientdb.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.komea.event.model.beans.FlatEvent;
import org.komea.event.storage.DateInterval;
import org.komea.event.storage.IEventDB;
import org.skife.jdbi.v2.ResultIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orientdb.session.impl.OrientSessionFactory;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.iterator.ORecordIteratorClass;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

public class OrientDBEventDB implements IEventDB {
	
	/**
	 * @author sleroy
	 */
	private final class EmptyResultIterator implements
	ResultIterator<FlatEvent> {
		@Override
		public void close() {

		}

		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public FlatEvent next() {
			return null;
		}

		@Override
		public void remove() {

		}
	}

	private final class OResultIteratorImplementation implements
	        ResultIterator<FlatEvent> {
		private final OrientDBEventIterator	iterator;
		
		private OResultIteratorImplementation(
		        final OrientDBEventIterator _iterator) {
			iterator = _iterator;
		}
		
		@Override
		public void close() {
			//
		}
		
		@Override
		public boolean hasNext() {
			
			return iterator.hasNext();
		}
		
		@Override
		public FlatEvent next() {
			
			return iterator.next();
		}
		
		@Override
		public void remove() {
			iterator.remove();
			
		}
	}
	
	private final OrientSessionFactory<ODatabaseDocumentTx>	orientSessionFactory;
	private final String	                                eventType;
	private static final Logger	                            LOGGER	= LoggerFactory
	                                                                       .getLogger(OrientDBEventDB.class);
	
	public OrientDBEventDB(
	        final OrientSessionFactory<ODatabaseDocumentTx> _orientSessionFactory,
	        final String _eventType) {
		orientSessionFactory = _orientSessionFactory;
		eventType = _eventType;
	}
	
	@Override
	public void close() throws IOException {
		// Do not close the osf.
		
	}
	
	@Override
	public long count() {
		try {
			return orientSessionFactory.getOrCreateDB().countClass(
			        eventType);
		} catch (final Exception e) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.warn(e.getMessage(), e);
			}
		}
		return 0L;
	}
	
	@Override
	public ResultIterator<FlatEvent> loadAll() {
		try {
			final ORecordIteratorClass<ODocument> browseClass = OrientDBEventDB.this.orientSessionFactory
			        .getOrCreateDB()
			        .browseClass(OrientDBEventDB.this.eventType);
			return new OResultIteratorImplementation(new OrientDBEventIterator(
			        browseClass.iterator()));
		} catch (final Exception e) {
			LOGGER.warn(e.getMessage(), e);
			return new EmptyResultIterator();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.komea.event.storage.IEventDB#loadOnPeriod(org.komea.event.storage
	 * .DateInterval)
	 */
	@Override
	public ResultIterator<FlatEvent> loadOnPeriod(final DateInterval _period) {
		List<ODocument> result = Collections.emptyList();
		if (_period.isCompleteInterval()) {
			final String query = "SELECT * FROM " + eventType
			        + " WHERE date BETWEEN ? AND ?";
			result = orientSessionFactory.getOrCreateDB()
			        .query(
			                new OSQLSynchQuery<>(query),
			                _period.getFrom(), _period.getTo());
			
		} else if (_period.hasFrom()) {
			final String query = "SELECT * FROM " + eventType
			        + " WHERE date > ?";
			result = orientSessionFactory.getOrCreateDB()
			        .query(
			                new OSQLSynchQuery<>(query),
			                _period.getFrom());
		} else if (_period.hasTo()) {
			final String query = "SELECT * FROM " + eventType
			        + " WHERE date < ?";
			result = orientSessionFactory.getOrCreateDB()
			        .query(
			                new OSQLSynchQuery<>(query),
			                _period.getTo());
		}
		return new OResultIteratorImplementation(new OrientDBEventIterator(
		        result.iterator()));
		
	}
	
	@Override
	public void put(final FlatEvent _flatEvent) {
		Validate.isTrue(eventType.equals(_flatEvent.getEventType()));
		final ODocument newInstance = orientSessionFactory.getOrCreateDB()
		        .newInstance(eventType);
		newInstance.fields((Map) _flatEvent.getProperties());
		newInstance.save();
		
	}
	
	@Override
	public void putAll(final Collection<FlatEvent> _values) {
		orientSessionFactory.getOrCreateDB().begin();
		for (final FlatEvent event : _values) {
			put(event);
		}
		orientSessionFactory.getOrCreateDB().commit();
		
	}

	@Override
	public void removeAll() {
		if (!orientSessionFactory.getOrCreateDB().getMetadata().getSchema()
		        .existsClass(eventType)) { return; }
		for (final ODocument event : orientSessionFactory.getOrCreateDB()
		        .browseClass(eventType)) {
			event.delete();
		}
		
	}
}
