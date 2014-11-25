package org.komea.orientdb.session.impl;

import java.util.List;

import org.komea.orientdb.session.IDocumentSessionFactory;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentPool;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

/**
 * A specific factory for creating OrientDocumentDatabaseFactory objects that
 * handle {@link ODatabaseDocumentTx}.
 *
 * @author Dzmitry_Naskou
 * @see ODatabaseDocumentTx
 */
public class OrientDocumentDatabaseFactory
		extends
		AbstractOrientDatabaseFactory<ODatabaseDocumentTx, ODatabaseDocumentPool>
		implements IDocumentSessionFactory {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.orm.orient.AbstractOrientDatabaseFactory#doCreatePool
	 * ()
	 */
	@Override
	protected ODatabaseDocumentPool doCreatePool() {
		return new ODatabaseDocumentPool(getUrl(), getUsername(), getPassword());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.orm.orient.AbstractOrientDatabaseFactory#newDatabase
	 * ()
	 */
	@Override
	protected ODatabaseDocumentTx newDatabase() {
		return new ODatabaseDocumentTx(getUrl());
	}

	@Override
	public ODocument newDocument() {

		return getOrCreateDatabaseSession().newInstance();
	}

	@Override
	public ODocument newDocument(final String className) {

		return getOrCreateDatabaseSession().newInstance(className);
	}

	@Override
	public List<ODocument> query(final String _sqlQuery) {

		return getOrCreateDatabaseSession().query(
				new OSQLSynchQuery<ODocument>(_sqlQuery));
	}

	@Override
	public void save(final ODocument _event) {
		getOrCreateDatabaseSession().save(_event);

	}
}