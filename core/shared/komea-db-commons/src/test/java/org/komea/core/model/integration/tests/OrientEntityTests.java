package org.komea.core.model.integration.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.IKomeaModelFactory;
import org.komea.core.model.impl.OKomeaModelFactory;
import org.komea.core.model.storage.impl.OGraphModelStorage;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.IKomeaSchemaFactory;
import org.komea.core.schema.IPrimitiveType.Primitive;
import org.komea.core.schema.IReference;
import org.komea.core.schema.impl.KomeaSchemaFactory;
import org.komea.orientdb.session.impl.OrientGraphDatabaseFactory;

public class OrientEntityTests {
	private IKomeaModelFactory mfactory;
	private IKomeaSchema schema;
	private OGraphModelStorage storage;

	@Before
	public void init() {
		IKomeaSchemaFactory sfactory = new KomeaSchemaFactory();
		this.schema = sfactory.newSchema("Test");

		final IEntityType type = sfactory.newEntity("Person");
		IReference name = sfactory.newAttribute("name", Primitive.STRING);
		type.addProperty(name);
		IReference values = sfactory.newAttribute("values", Primitive.INTEGER).setMany(true);
		type.addProperty(values);
		IReference references = sfactory.newReference("family", type).setMany(
				true);
		type.addProperty(references);
		this.schema.addType(type);

		OrientGraphDatabaseFactory sessionsFactory = new OrientGraphDatabaseFactory();
		sessionsFactory.setUrl("memory:test");
		sessionsFactory.setUsername("admin");
		sessionsFactory.setPassword("admin");
		sessionsFactory.init();

		this.storage = new OGraphModelStorage(this.schema, sessionsFactory);

		this.mfactory = new OKomeaModelFactory(this.storage);

	}

	@After
	public void end() throws IOException {
		this.storage.close();
	}

	@Test
	public void setAttributeTest() {
		IEntityType type = this.schema.findType("Person");

		IKomeaEntity p1 = this.mfactory.newInstance(type);

		p1.set("name", "John Doe");

		String name = p1.value("name");
		assertEquals("John Doe", name);
	}

	@Test
	public void setReferenceTest() {
		IEntityType type = this.schema.findType("Person");

		IKomeaEntity p1 = this.mfactory.newInstance(type);
		p1.set("name", "John");

		IKomeaEntity p2 = this.mfactory.newInstance(type);
		p2.set("name", "Bob");
		p1.add("family", p2);
		
		IKomeaEntity p3 = this.mfactory.newInstance(type);
		p3.set("name", "Bobby");
		
		p2.add("family", p3);

		Iterable<IKomeaEntity> references = p1.references("family");
		assertTrue(references.iterator().hasNext());

		IKomeaEntity next = references.iterator().next();
		assertEquals("Bob", next.value("name"));
	}

	@Test
	public void addInAttributeTest() {
		IEntityType type = this.schema.findType("Person");

		IKomeaEntity p1 = this.mfactory.newInstance(type);

		p1.add("values", 1);
		p1.add("values", 2);
		p1.add("values", 3);

		Collection<?> values = p1.value("values");
		assertEquals(3, values.size());
	}
}
