package org.komea.core.model.services.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
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

public class OrientdbStorageServiceTests {
	OrientGraphDatabaseFactory sessionsFactory;
	IKomeaSchemaFactory factory;

	@Before
	public void init() {
		this.sessionsFactory = new OrientGraphDatabaseFactory();

		DatabaseConfiguration databaseConfiguration = new MemoryDatabaseConfiguration(
				"test");
		this.sessionsFactory.init(databaseConfiguration);
		this.factory = new KomeaSchemaFactory();
	}

	public void end() throws IOException {
		this.sessionsFactory.getGraph().drop();
		this.sessionsFactory.close();
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
		company.addProperty(this.factory.newReference("members", person)
				.setMany(true).setContainment(true));
		company.addProperty(this.factory.newAttribute("values",
				Primitive.INTEGER).setMany(true));
		schema.addType(company);

		final OKomeaGraphStorage service = new OKomeaGraphStorage(schema,
				this.sessionsFactory);

		// Entities of the schema must be in graph as a vertex type
		assertNotNull(service.getGraph().getVertexType(dev.getName()));

		// add an entity in the schema
		final IEntityType resource = this.factory.newEntity("Resource");
		schema.addType(resource);
		service.update(schema);

		// Entities of the updated schema must be in graph as a vertex type
		assertNotNull(service.getGraph().getVertexType(resource.getName()));

		// update an entity of the schema
		person.addProperty(this.factory
				.newAttribute("adress", Primitive.STRING));

		// updated entity must be modified in the graph
		service.update(schema);
		final OrientVertexType personVertextype = service.getGraph()
				.getVertexType(person.getName());
		assertTrue(personVertextype.existsProperty("adress"));
	}
}
