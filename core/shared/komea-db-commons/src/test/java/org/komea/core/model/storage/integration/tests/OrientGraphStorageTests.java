package org.komea.core.model.storage.integration.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.impl.OKomeaModelFactory;
import org.komea.core.model.storage.IKomeaGraphStorage;
import org.komea.core.model.storage.impl.OKomeaGraphStorage;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.IKomeaSchemaFactory;
import org.komea.core.schema.Primitive;
import org.komea.core.schema.ReferenceArity;
import org.komea.core.schema.ReferenceKind;
import org.komea.core.schema.impl.KomeaSchemaFactory;
import org.springframework.orientdb.session.impl.DatabaseConfiguration;
import org.springframework.orientdb.session.impl.OrientSessionFactory;
import org.springframework.orientdb.session.impl.TestDatabaseConfiguration;

import com.tinkerpop.blueprints.impls.orient.OrientVertexType;

public class OrientGraphStorageTests {

	private final IKomeaSchemaFactory	factory	= new KomeaSchemaFactory();

	private IKomeaSchema	          schema;
	private IKomeaGraphStorage	      storage;

	private OrientSessionFactory	  osf;

	@After
	public void end() throws IOException {
		// this.storage.close();
		this.osf.close();
	}

	@Test
	public void getAllEntitiesTest() {

		final IEntityType person = this.schema.findType("Person");
		final OKomeaModelFactory localFactory = new OKomeaModelFactory(this.storage);
		localFactory.create(person);
		assertTrue(this.storage.entities().iterator().hasNext());
	}

	@Test
	public void getEntitiesTest() {
		final IEntityType person = this.schema.findType("Person");
		final OKomeaModelFactory localFactory = new OKomeaModelFactory(this.storage);
		localFactory.create(person);
		assertTrue(this.storage.entities(person).iterator().hasNext());
	}

	@Before
	public void init() {
		// create tests shared schema
		this.schema = this.factory.newSchema("company");
		final IEntityType person = this.factory.newEntity("Person");
		person.addProperty(this.factory.newAttribute("name", Primitive.STRING));
		final IEntityType dev = this.factory.newEntity("Developper");
		dev.setSuperType(person);
		this.schema.addType(dev);
		this.schema.addType(person);

		final IEntityType company = this.factory.newEntity("Company");
		company.addProperty(this.factory.newReference("members", person).setArity(ReferenceArity.MANY)
				.setKind(ReferenceKind.CONTAINMENT));
		company.addProperty(this.factory.newAttribute("values", Primitive.INTEGER).setArity(ReferenceArity.MANY));
		this.schema.addType(company);

		// initialize storage
		final DatabaseConfiguration db = new TestDatabaseConfiguration();

		this.osf = new OrientSessionFactory(db);
		this.storage = new OKomeaGraphStorage(this.schema, this.osf.getGraphTx());

	}

	@Test
	public void removeTest() {
		final IEntityType person = this.schema.findType("Person");
		final OKomeaModelFactory localFactory = new OKomeaModelFactory(this.storage);
		final IKomeaEntity instance = localFactory.create(person);
		assertTrue(this.storage.entities(person).iterator().hasNext());
		this.storage.delete(instance);
		assertFalse(this.storage.entities(person).iterator().hasNext());
	}

	@Test
	public void schemaUpdateTest() {

		final IEntityType person = this.schema.findType("Person");
		final IEntityType dev = this.schema.findType("Developper");

		// Entities of the schema must be in graph as a vertex type
		assertNotNull(this.storage.getGraph().getVertexType(dev.getName()));

		// add an entity in the schema
		final IEntityType resource = this.factory.newEntity("Resource");
		this.schema.addType(resource);
		this.storage.update(this.schema);

		// Entities of the updated schema must be in graph as a vertex type
		assertNotNull(this.storage.getGraph().getVertexType(resource.getName()));

		// update an entity of the schema
		person.addProperty(this.factory.newAttribute("adress", Primitive.STRING));

		// updated entity must be modified in the graph
		this.storage.update(this.schema);
		final OrientVertexType personVertextype = this.storage.getGraph().getVertexType(person.getName());
		assertTrue(personVertextype.existsProperty("adress"));

	}

	@Test
	public void testCreate() {
		final IEntityType dev = this.schema.findType("Developper");
		final IKomeaEntity d1 = this.storage.create(dev);
		d1.set("name", "Bob");
		assertEquals("Bob", d1.value("name"));
	}

	@Test
	public void testGetOrCreate() {
		final IEntityType dev = this.schema.findType("Developper");

		// if the property is not a unique index: fail
		try {
			this.storage.getOrCreate(dev, "name", "Bob");
			fail("Property is not an unique index: it should fail.");
		} catch (final IllegalArgumentException e) {

		}

		// update the schema to enable unique
		dev.findProperty("name").enableUnique();
		this.storage.update(this.schema);

		final IKomeaEntity d1 = this.storage.getOrCreate(dev, "name", "Bob");
		assertEquals("Bob", d1.value("name"));
	}

	@Test
	public void testGetOrCreate2() {
		final IEntityType dev = this.schema.findType("Developper");

		// if the property is not a unique index: fail
		try {
			this.storage.getOrCreate(dev, "name", "Bob");
			fail("Property is not an unique index: it should fail.");
		} catch (final IllegalArgumentException e) {

		}

		// update the schema to enable unique
		dev.findProperty("name").enableUnique();
		this.storage.update(this.schema);

		final IKomeaEntity d1 = this.storage.getOrCreate(dev, "name", "Bob");
		assertEquals("Bob", d1.value("name"));
	}

	@Test
	public void testIndexation() {
		final IEntityType dev = this.schema.findType("Developper");

		// if the property is not a unique index: fail
		try {
			this.storage.find(dev, "name", "Bob");
			fail("Property is not an  index: it should fail.");
		} catch (final IllegalArgumentException e) {
		}

		// update the schema to enable unique
		dev.findProperty("name").enableIndexation();
		this.storage.update(this.schema);

		final IKomeaEntity d1 = this.storage.create(dev);
		d1.set("name", "Bob");
		final IKomeaEntity d2 = this.storage.create(dev);
		d2.set("name", "Bob");

		int i = 0;
		final Iterable<IKomeaEntity> found = this.storage.find(dev, "name", "Bob");
		final Iterator<IKomeaEntity> iterator = found.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			i++;
		}
		assertEquals(2, i);
	}
}
