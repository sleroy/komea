package org.komea.core.model.integration.tests;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.komea.core.model.IKomeaEntityFactory;
import org.komea.core.model.impl.OKomeaModelFactory;
import org.komea.core.model.storage.IKomeaGraphStorage;
import org.komea.core.model.storage.impl.OKomeaGraphStorage;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.impl.KomeaSchemaFactory;
import org.springframework.orientdb.session.impl.DatabaseConfiguration;
import org.springframework.orientdb.session.impl.OrientSessionFactory;
import org.springframework.orientdb.session.impl.TestDatabaseConfiguration;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;

public abstract class AbstractIntegrationTest {
	private IKomeaEntityFactory	 mfactory;
	private IKomeaSchema	     schema;
	private IKomeaGraphStorage	 storage;
	private OrientSessionFactory	sessionsFactory;
	private OrientGraph	         graph;
	private KomeaSchemaFactory	 schemaFactory;

	@After
	public final void end() throws IOException {
		this.graph.drop();
		if (this.sessionsFactory != null) {
			this.sessionsFactory.close();
		}
	}

	public OrientGraph getGraph() {
		return this.graph;
	}

	public IKomeaEntityFactory getMfactory() {
		return this.mfactory;
	}

	public IKomeaSchema getSchema() {
		return this.schema;
	}

	public KomeaSchemaFactory getSchemaFactory() {
		return this.schemaFactory;
	}

	public OrientSessionFactory getSessionsFactory() {
		return this.sessionsFactory;
	}

	public IKomeaGraphStorage getStorage() {
		return this.storage;

	}

	@Before
	public final void init() {
		this.schemaFactory = new KomeaSchemaFactory();
		this.schema = this.schemaFactory.newSchema("Test");

		this.initSchema();

		this.sessionsFactory = new OrientSessionFactory();
		final DatabaseConfiguration databaseConfiguration = new TestDatabaseConfiguration();
		this.sessionsFactory.init(databaseConfiguration);
		this.graph = this.sessionsFactory.getGraphTx();
		this.storage = new OKomeaGraphStorage(this.schema, this.graph);

		this.mfactory = new OKomeaModelFactory(this.storage);

	}

	public void setSfactory(final KomeaSchemaFactory _sfactory) {
		this.schemaFactory = _sfactory;
	}

	protected abstract void initSchema();

}