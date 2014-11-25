package org.komea.orientdb.session.impl;

import org.komea.orientdb.session.IGraphSessionFactory;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentPool;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

public class OrientGraphDatabaseFactory
		extends
		AbstractOrientDatabaseFactory<ODatabaseDocumentTx, ODatabaseDocumentPool>
		implements IGraphSessionFactory {

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
	 * org.komea.product.eventory.database.conf.IOrientDbGraphSessionFactory
	 * #getGraph()
	 */
	@Override
	public OrientGraph getGraph() {
		return new OrientGraph(getOrCreateDatabaseSession());
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
}