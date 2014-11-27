package org.komea.orientdb.session.impl;

import java.util.Iterator;
import java.util.List;

import org.komea.orientdb.session.IDocumentSessionFactory;
import org.komea.orientdb.session.document.IODocument;

import com.google.common.collect.Iterators;
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

	public OrientDocumentDatabaseFactory() {
		super();
	}

	public OrientDocumentDatabaseFactory(
			final DatabaseConfiguration _configuration) {
		super(_configuration);
	}

	@Override
	public IODocument newDocument() {

		return new ODocumentProxy(this.getOrCreateDatabaseSession()
				.newInstance());
	}

	@Override
	public IODocument newDocument(final String className) {

		return new ODocumentProxy(this.getOrCreateDatabaseSession()
				.newInstance(className));
	}

	@Override
	public Iterator<IODocument> query(final String _query) {
		final List<ODocument> results = this.rawQuery(_query);
		return Iterators
				.transform(results.iterator(), new IODocumentFunction());
	}

	@Override
	public List<ODocument> rawQuery(final String _sqlQuery) {

		return this.getOrCreateDatabaseSession().query(
				new OSQLSynchQuery<ODocument>(_sqlQuery));
	}

	@Override
	public void save(final ODocument _event) {
		this.getOrCreateDatabaseSession().save(_event);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.orm.orient.AbstractOrientDatabaseFactory#doCreatePool
	 * ()
	 */
	@Override
	protected ODatabaseDocumentPool doCreatePool(
			final DatabaseConfiguration _configuration) {
		return new ODatabaseDocumentPool(_configuration.getUrl(),
				_configuration.getUsername(), _configuration.getPassword());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.orm.orient.AbstractOrientDatabaseFactory#newDatabase
	 * ()
	 */
	@Override
	protected ODatabaseDocumentTx newDatabase(
			final DatabaseConfiguration _configuration) {
		return new ODatabaseDocumentTx(_configuration.getUrl());
	}
}