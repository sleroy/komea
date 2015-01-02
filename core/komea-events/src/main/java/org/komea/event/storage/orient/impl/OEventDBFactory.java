package org.komea.event.storage.orient.impl;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventDBFactory;
import org.springframework.orientdb.session.impl.DatabaseConfiguration;
import org.springframework.orientdb.session.impl.OrientSessionFactory;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

/**
 * This class provides an implementation of the event storage with OrientDB.
 *
 * @author sleroy
 */
public class OEventDBFactory implements IEventDBFactory {
	
	private final OrientSessionFactory<ODatabaseDocumentTx>	orientSessionFactory;
	private DatabaseConfiguration	                        databaseConfiguration;
	
	private final Map<String, IEventDB>	                    eventsDB	= new ConcurrentHashMap<>();
	
	public OEventDBFactory(final DatabaseConfiguration _databaseConfiguration) {
		this(new OrientSessionFactory(_databaseConfiguration));
	}
	
	public OEventDBFactory(
			final OrientSessionFactory<ODatabaseDocumentTx> _orientSessionFactory) {
		orientSessionFactory = _orientSessionFactory;
		
	}
	
	@Override
	public void close() throws IOException {
		for (final IEventDB db : eventsDB.values()) {
			db.close();
		}
		orientSessionFactory.close();
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.komea.event.storage.IEventDBFactory#declareEventType(java.lang.String
	 * )
	 */
	@Override
	public void declareEventType(final String _type) {
		orientSessionFactory.getOrCreateDB().getMetadata().getSchema()
		.createClass(_type);
		
	}

	@Override
	public IEventDB getEventDB(final String _storageName) {
		IEventDB iEventDB = eventsDB.get(_storageName);
		if (iEventDB == null) {
			final OrientDBEventDB orientDBEventDB = new OrientDBEventDB(
					orientSessionFactory, _storageName);
			eventsDB.put(_storageName, orientDBEventDB);
			iEventDB = orientDBEventDB;
		}

		return iEventDB;
	}
	
}
