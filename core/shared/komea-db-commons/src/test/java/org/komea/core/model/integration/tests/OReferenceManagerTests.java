package org.komea.core.model.integration.tests;

import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.IKomeaModelFactory;
import org.komea.core.model.impl.OEntityReferenceManager;
import org.komea.core.model.impl.OKomeaEntity;
import org.komea.core.model.impl.OKomeaModelFactory;
import org.komea.core.model.storage.impl.OGraphModelStorage;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.IKomeaSchemaFactory;
import org.komea.core.schema.IPrimitiveType.Primitive;
import org.komea.core.schema.IReference;
import org.komea.core.schema.impl.KomeaSchemaFactory;
import org.komea.orientdb.session.impl.OrientGraphDatabaseFactory;

public class OReferenceManagerTests {
	private OGraphModelStorage storage;
	private IKomeaSchemaFactory sfactory;
	private IKomeaModelFactory mfactory;
	private IEntityType type;

	@Before
	public void init() {
		this.sfactory = new KomeaSchemaFactory();

		this.type = this.sfactory.newEntity("Person");
		this.type.addProperty(this.sfactory.newAttribute("name",
				Primitive.STRING));
		this.type.addProperty(this.sfactory.newReference("partner", this.type));
		this.type.addProperty(this.sfactory.newReference("family", this.type)
				.setMany(true));

		IKomeaSchema schema = this.sfactory.newSchema("Test");
		schema.addType(this.type);

		OrientGraphDatabaseFactory sessionsFactory = new OrientGraphDatabaseFactory();
		sessionsFactory.setUrl("memory:test");
		sessionsFactory.setUsername("admin");
		sessionsFactory.setPassword("admin");
		sessionsFactory.init();

		this.storage = new OGraphModelStorage(schema, sessionsFactory);

		this.mfactory = new OKomeaModelFactory(this.storage);

	}

	@After
	public void end() throws IOException {
		this.storage.close();
	}

	@Test
	public void setEntityReferenceTest() {
		OKomeaEntity p1 = (OKomeaEntity) this.mfactory.newInstance(this.type);
		OKomeaEntity p2 = (OKomeaEntity) this.mfactory.newInstance(this.type);

		IReference property = this.type.findProperty("partner");
		OEntityReferenceManager updater = new OEntityReferenceManager(p1,
				property);
		updater.set(p2);

		Assert.assertEquals(p2, updater.get());
	}

	@Test
	public void addEntityReferenceTest() {
		OKomeaEntity p1 = (OKomeaEntity) this.mfactory.newInstance(this.type);
		OKomeaEntity p2 = (OKomeaEntity) this.mfactory.newInstance(this.type);

		IReference property = this.type.findProperty("family");
		OEntityReferenceManager updater = new OEntityReferenceManager(p1,
				property);
		updater.add(p2);
		Iterable<IKomeaEntity> family = updater.get();
		Assert.assertTrue(family.iterator().hasNext());
	}
}
