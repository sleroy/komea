package org.komea.event.storage.orientdb.impl;

import java.io.IOException;

import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventDBFactory;
import org.springframework.orientdb.session.impl.DatabaseConfiguration;
import org.springframework.orientdb.session.impl.OrientSessionFactory;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

/**
 * This class provides an implementation of the event storage with OrientDB.
 *
 * @author sleroy
 *
 */
public class OEventDBFactory implements IEventDBFactory {

	private final OrientSessionFactory<ODatabaseDocumentTx>	orientSessionFactory;
	private DatabaseConfiguration	                        databaseConfiguration;

	public OEventDBFactory(final DatabaseConfiguration _databaseConfiguration) {
		this(new OrientSessionFactory(_databaseConfiguration));
	}

	public OEventDBFactory(final OrientSessionFactory<ODatabaseDocumentTx> _orientSessionFactory) {
		this.orientSessionFactory = _orientSessionFactory;

	}

	@Override
	public void close() throws IOException {
		this.orientSessionFactory.close();

	}



	@Override
	public IEventDB getEventDB(final String _storageName) {

		return new OrientDBEventDB(this.orientSessionFactory, _storageName);
	}

}
