package org.komea.product.eventory.database.session;

import org.komea.product.eventory.database.session.api.IGraphSessionFactory;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentPool;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

/**
 * A specific factory for creating OrientObjectDatabase objects that handle
 * {@link OObjectDatabaseTx}.
 *
 * @author Dzmitry_Naskou
 * @see OObjectDatabaseTx
 */
public class OrientGraphDatabaseFactory
extends
AbstractOrientDatabaseFactory<ODatabaseDocumentTx, ODatabaseDocumentPool>
implements IGraphSessionFactory {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.komea.product.eventory.database.conf.IOrientDbGraphSessionFactory
	 * #getGraph()
	 */
	@Override
	public OrientGraph getGraph() {
		return new OrientGraph(this.getPool());
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