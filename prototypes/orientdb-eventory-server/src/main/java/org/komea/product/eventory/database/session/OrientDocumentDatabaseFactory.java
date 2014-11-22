package org.komea.product.eventory.database.session;

import java.util.List;

import org.komea.product.eventory.database.session.api.IDocumentSessionFactory;

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

	@Override
	public ODocument newDocument() {

		return this.getOrCreateDatabaseSession().newInstance();
	}

	@Override
	public ODocument newDocument(final String className) {

		return this.getOrCreateDatabaseSession().newInstance(className);
	}

	@Override
	public List<ODocument> query(final String _sqlQuery) {

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
	protected ODatabaseDocumentPool doCreatePool() {
		return new ODatabaseDocumentPool(this.getUrl(), this.getUsername(),
				this.getPassword());
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
		return new ODatabaseDocumentTx(this.getUrl());
	}
}