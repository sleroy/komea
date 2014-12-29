package org.komea.event.storage.orientdb.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.komea.event.model.beans.FlatEvent;
import org.komea.event.storage.IEventDB;
import org.skife.jdbi.v2.ResultIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orientdb.session.impl.OrientSessionFactory;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.iterator.ORecordIteratorClass;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class OrientDBEventDB implements IEventDB {

	private final class OResultIteratorImplementation implements ResultIterator<FlatEvent> {
	    private final OrientDBEventIterator	iterator;

	    private OResultIteratorImplementation(OrientDBEventIterator _iterator) {
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
	private static final Logger	                            LOGGER	= LoggerFactory.getLogger(OrientDBEventDB.class);

	public OrientDBEventDB(final OrientSessionFactory<ODatabaseDocumentTx> _orientSessionFactory,
	        final String _eventType) {
		this.orientSessionFactory = _orientSessionFactory;
		this.eventType = _eventType;
	}

	@Override
	public void close() throws IOException {
		// Do not close the osf.

	}

	@Override
	public long count() {
		try {
			return this.orientSessionFactory.getOrCreateDB().countClass(this.eventType);
		} catch (final Exception e) {
			if (LOGGER.isDebugEnabled())
			LOGGER.warn(e.getMessage(), e);
		}
		return 0L;
	}

	@Override
	public ResultIterator<FlatEvent> loadAll() {

		final ORecordIteratorClass<ODocument> browseClass = OrientDBEventDB.this.orientSessionFactory.getOrCreateDB()
		        .browseClass(OrientDBEventDB.this.eventType);
		return new OResultIteratorImplementation(new OrientDBEventIterator(browseClass.iterator()));
	}

	@Override
	public void put(final FlatEvent _flatEvent) {
		Validate.isTrue(this.eventType.equals(_flatEvent.getEventType()));
		final ODocument newInstance = this.orientSessionFactory.getOrCreateDB().newInstance(this.eventType);
		newInstance.fields((Map) _flatEvent.getProperties());
		newInstance.save();

	}

	@Override
	public void putAll(final Collection<FlatEvent> _values) {
		this.orientSessionFactory.getOrCreateDB().begin();
		for (final FlatEvent event : _values) {
			this.put(event);
		}
		this.orientSessionFactory.getOrCreateDB().commit();

	}

	@Override
	public void removeAll() {
		if (!orientSessionFactory.getOrCreateDB().getMetadata().getSchema().existsClass(eventType)) return ;
		for (final ODocument event : this.orientSessionFactory.getOrCreateDB().browseClass(this.eventType)) {
			event.delete();
		}

	}
}
