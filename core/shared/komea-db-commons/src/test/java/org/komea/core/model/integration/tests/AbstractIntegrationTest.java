package org.komea.core.model.integration.tests;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.komea.core.model.IKomeaEntityFactory;
import org.komea.core.model.impl.OKomeaModelFactory;
import org.komea.core.model.storage.IKomeaGraphStorage;
import org.komea.core.model.storage.impl.OKomeaGraphStorage;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.IKomeaSchemaFactory;
import org.komea.core.schema.IReference;
import org.komea.core.schema.Primitive;
import org.komea.core.schema.ReferenceArity;
import org.komea.core.schema.ReferenceKind;
import org.komea.core.schema.impl.KomeaSchemaFactory;
import org.komea.orientdb.session.impl.DatabaseConfiguration;
import org.komea.orientdb.session.impl.OrientGraphDatabaseFactory;
import org.komea.orientdb.session.impl.TestDatabaseConfiguration;

public class AbstractIntegrationTest {
	protected IKomeaEntityFactory mfactory;
	protected IKomeaSchema schema;
	protected IKomeaGraphStorage storage;
	private OrientGraphDatabaseFactory sessionsFactory;

	@After
	public void end() throws IOException {
		this.sessionsFactory.getGraph().drop();
		this.sessionsFactory.close();
	}

	@Before
	public void init() {
		final IKomeaSchemaFactory sfactory = new KomeaSchemaFactory();
		this.schema = sfactory.newSchema("Test");

		final IEntityType type = sfactory.newEntity("Person");
		final IReference name = sfactory.newAttribute("name", Primitive.STRING);
		type.addProperty(name);
		final IReference references = sfactory.newReference("children", type)
				.setArity(ReferenceArity.MANY)
				.setKind(ReferenceKind.AGGREGATION);
		type.addProperty(references);
		this.schema.addType(type);

		this.sessionsFactory = new OrientGraphDatabaseFactory();
		final DatabaseConfiguration databaseConfiguration = new TestDatabaseConfiguration();
		this.sessionsFactory.init(databaseConfiguration);

		this.storage = new OKomeaGraphStorage(this.schema, this.sessionsFactory);

		this.mfactory = new OKomeaModelFactory(this.storage);

	}
}
