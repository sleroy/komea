package org.komea.core.model.storage.integration.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.impl.OKomeaModelFactory;
import org.komea.core.model.storage.impl.OKomeaGraphStorage;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.IKomeaSchemaFactory;
import org.komea.core.schema.IPrimitiveType.Primitive;
import org.komea.core.schema.impl.KomeaSchemaFactory;
import org.komea.orientdb.session.impl.DatabaseConfiguration;
import org.komea.orientdb.session.impl.MemoryDatabaseConfiguration;
import org.komea.orientdb.session.impl.OrientGraphDatabaseFactory;

import com.tinkerpop.blueprints.impls.orient.OrientVertexType;

public class OrientGraphStorageTests {
	OrientGraphDatabaseFactory	sessionsFactory;
	IKomeaSchemaFactory	       factory;

	@After
	public void end() throws IOException {
		this.sessionsFactory.getGraph().drop();
		this.sessionsFactory.close();
	}

	@Test
	public void getAllEntitiesTest() {
		final IKomeaSchema schema = this.factory.newSchema("company");
		final IEntityType person = this.factory.newEntity("Person");
		schema.addType(person);

		@SuppressWarnings("resource")
		final OKomeaGraphStorage storage = new OKomeaGraphStorage(schema, this.sessionsFactory);
		storage.update(schema);

		final OKomeaModelFactory localFactory = new OKomeaModelFactory(storage);
		localFactory.newInstance(person);
		assertTrue(storage.entities().iterator().hasNext());
	}

	@Test
	public void getEntitiesTest() {
		final IKomeaSchema schema = this.factory.newSchema("company");
		final IEntityType person = this.factory.newEntity("Person");
		schema.addType(person);
		@SuppressWarnings("resource")
		final OKomeaGraphStorage storage = new OKomeaGraphStorage(schema, this.sessionsFactory);
		storage.update(schema);

		final OKomeaModelFactory localFactory = new OKomeaModelFactory(storage);
		localFactory.newInstance(person);
		assertTrue(storage.entities(person).iterator().hasNext());
	}

	@Before
	public void init() {
		this.sessionsFactory = new OrientGraphDatabaseFactory();
		final DatabaseConfiguration conf = new MemoryDatabaseConfiguration("test");
		this.sessionsFactory.init(conf);
		this.factory = new KomeaSchemaFactory();

	}

	@Test
	public void removeTest() {
		final IKomeaSchema schema = this.factory.newSchema("company");
		final IEntityType person = this.factory.newEntity("Person");
		schema.addType(person);
		@SuppressWarnings("resource")
		final OKomeaGraphStorage storage = new OKomeaGraphStorage(schema, this.sessionsFactory);
		storage.update(schema);

		final OKomeaModelFactory localFactory = new OKomeaModelFactory(storage);
		final IKomeaEntity instance = localFactory.newInstance(person);
		assertTrue(storage.entities(person).iterator().hasNext());
		storage.delete(instance);
		assertFalse(storage.entities(person).iterator().hasNext());
	}

	@Test
	public void schemaUpdateTest() {

		final IKomeaSchema schema = this.factory.newSchema("company");

		final IEntityType person = this.factory.newEntity("Person");
		person.addProperty(this.factory.newAttribute("name", Primitive.STRING));
		final IEntityType dev = this.factory.newEntity("Developper");
		dev.setSuperType(person);
		schema.addType(dev);
		schema.addType(person);

		final IEntityType company = this.factory.newEntity("Company");
		company.addProperty(this.factory.newReference("members", person).setContainment(true).setMany(true));
		company.addProperty(this.factory.newAttribute("values", Primitive.INTEGER).setMany(true));
		schema.addType(company);

		final OKomeaGraphStorage service = new OKomeaGraphStorage(schema, this.sessionsFactory);

		// Entities of the schema must be in graph as a vertex type
		assertNotNull(service.getGraph().getVertexType(dev.getName()));

		// add an entity in the schema
		final IEntityType resource = this.factory.newEntity("Resource");
		schema.addType(resource);
		service.update(schema);

		// Entities of the updated schema must be in graph as a vertex type
		assertNotNull(service.getGraph().getVertexType(resource.getName()));

		// update an entity of the schema
		person.addProperty(this.factory.newAttribute("adress", Primitive.STRING));

		// updated entity must be modified in the graph
		service.update(schema);
		final OrientVertexType personVertextype = service.getGraph().getVertexType(person.getName());
		assertTrue(personVertextype.existsProperty("adress"));
	}
}
