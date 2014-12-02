package org.komea.core.model.integration.tests;

import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.IKomeaEntityFactory;
import org.komea.core.model.impl.OEntityReferenceManager;
import org.komea.core.model.impl.OKomeaEntity;
import org.komea.core.model.impl.OKomeaModelFactory;
import org.komea.core.model.storage.IKomeaGraphStorage;
import org.komea.core.model.storage.impl.OKomeaGraphStorage;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.IKomeaSchemaFactory;
import org.komea.core.schema.IReference;
import org.komea.core.schema.Primitive;
import org.komea.core.schema.ReferenceArity;
import org.komea.core.schema.impl.KomeaSchemaFactory;
import org.komea.orientdb.session.impl.DatabaseConfiguration;
import org.komea.orientdb.session.impl.OrientGraphDatabaseFactory;
import org.komea.orientdb.session.impl.TestDatabaseConfiguration;

public class OReferenceManagerTests {
	private IKomeaGraphStorage storage;
	private IKomeaSchemaFactory sfactory;
	private IKomeaEntityFactory mfactory;
	private IEntityType type;
	private OrientGraphDatabaseFactory sessionsFactory;

	@Test
	public void addEntityReferenceTest() {
		final OKomeaEntity p1 = (OKomeaEntity) this.mfactory.create(this.type);
		final OKomeaEntity p2 = (OKomeaEntity) this.mfactory.create(this.type);

		final IReference property = this.type.findProperty("family");
		final OEntityReferenceManager updater = new OEntityReferenceManager(p1,
				property);
		updater.addReference(p2);
		final Iterable<IKomeaEntity> family = updater.get();
		Assert.assertTrue(family.iterator().hasNext());
	}

	@After
	public void end() throws IOException {
		this.sessionsFactory.getGraph().drop();
		this.sessionsFactory.close();
	}

	@Before
	public void init() {
		this.sfactory = new KomeaSchemaFactory();

		this.type = this.sfactory.newEntity("Person");
		this.type.addProperty(this.sfactory.newAttribute("name",
				Primitive.STRING));
		this.type.addProperty(this.sfactory.newReference("partner", this.type));
		this.type.addProperty(this.sfactory.newReference("family", this.type)
				.setArity(ReferenceArity.MANY));

		final IKomeaSchema schema = this.sfactory.newSchema("Test");
		schema.addType(this.type);

		this.sessionsFactory = new OrientGraphDatabaseFactory();
		final DatabaseConfiguration databaseConfiguration = new TestDatabaseConfiguration();
		this.sessionsFactory.init(databaseConfiguration);

		this.storage = new OKomeaGraphStorage(schema, this.sessionsFactory);

		this.mfactory = new OKomeaModelFactory(this.storage);

	}

	@Test
	public void setEntityReferenceTest() {
		final OKomeaEntity p1 = (OKomeaEntity) this.mfactory.create(this.type);
		final OKomeaEntity p2 = (OKomeaEntity) this.mfactory.create(this.type);

		final IReference property = this.type.findProperty("partner");
		final OEntityReferenceManager updater = new OEntityReferenceManager(p1,
				property);
		updater.set(p2);

		Assert.assertEquals(p2, updater.get());
	}
}
