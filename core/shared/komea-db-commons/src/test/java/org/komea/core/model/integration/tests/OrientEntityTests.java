package org.komea.core.model.integration.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.IKomeaEntityFactory;
import org.komea.core.model.impl.OKomeaModelFactory;
import org.komea.core.model.storage.IKomeaGraphStorage;
import org.komea.core.model.storage.impl.OKomeaGraphStorage;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.IKomeaSchemaFactory;
import org.komea.core.schema.IPrimitiveType.Primitive;
import org.komea.core.schema.IReference;
import org.komea.core.schema.ReferenceArity;
import org.komea.core.schema.impl.KomeaSchemaFactory;
import org.komea.orientdb.session.impl.DatabaseConfiguration;
import org.komea.orientdb.session.impl.OrientGraphDatabaseFactory;
import org.komea.orientdb.session.impl.TestDatabaseConfiguration;

public class OrientEntityTests {
	private IKomeaEntityFactory mfactory;
	private IKomeaSchema schema;
	private IKomeaGraphStorage storage;
	private OrientGraphDatabaseFactory sessionsFactory;

	@Before
	public void init() {
		IKomeaSchemaFactory sfactory = new KomeaSchemaFactory();
		this.schema = sfactory.newSchema("Test");

		final IEntityType type = sfactory.newEntity("Person");
		IReference name = sfactory.newAttribute("name", Primitive.STRING);
		name.enableIndexation();
		type.addProperty(name);
		IReference values = sfactory.newAttribute("values", Primitive.INTEGER).setArity(ReferenceArity.MANY);
		type.addProperty(values);
		IReference references = sfactory.newReference("family", type).setArity(ReferenceArity.MANY);
		type.addProperty(references);
		this.schema.addType(type);

		this.sessionsFactory = new OrientGraphDatabaseFactory();
		DatabaseConfiguration databaseConfiguration = new TestDatabaseConfiguration();
		this.sessionsFactory.init(databaseConfiguration);

		this.storage = new OKomeaGraphStorage(this.schema, this.sessionsFactory);

		this.mfactory = new OKomeaModelFactory(this.storage);

	}

	@After
	public void end() throws IOException {
		this.sessionsFactory.getGraph().drop();
		this.sessionsFactory.close();
	}

	@Test
	public void setAttributeTest() {
		IEntityType type = this.schema.findType("Person");
		
		IKomeaEntity p1 = this.mfactory.create(type);
		
		p1.set("name", "John Doe");

		String name = p1.value("name");
		assertEquals("John Doe", name);
	}

	@Test
	public void setReferenceTest() {
		IEntityType type = this.schema.findType("Person");

		IKomeaEntity p1 = this.mfactory.create(type);
		p1.set("name", "John");

		IKomeaEntity p2 = this.mfactory.create(type);
		p2.set("name", "Bob");
		p1.add("family", p2);

		IKomeaEntity p3 = this.mfactory.create(type);
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

		IKomeaEntity p1 = this.mfactory.create(type);

		p1.add("values", 1);
		p1.add("values", 2);
		p1.add("values", 3);

		Collection<?> values = p1.value("values");
		assertEquals(3, values.size());
	}
}
