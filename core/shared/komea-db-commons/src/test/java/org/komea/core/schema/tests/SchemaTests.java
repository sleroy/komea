package org.komea.core.schema.tests;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchemaFactory;
import org.komea.core.schema.IReference;
import org.komea.core.schema.Primitive;
import org.komea.core.schema.impl.KomeaSchemaFactory;

public class SchemaTests {
	private IKomeaSchemaFactory factory;

	@Before
	public void init() {
		this.factory = new KomeaSchemaFactory();
	}

	@Test
	public void testEntityFindProperty() {
		final IEntityType entityType = this.factory.newEntity("MyEntity");
		final IReference attribute = this.factory.newAttribute("att",
				Primitive.STRING);
		entityType.addProperty(attribute);
		assertNotNull(entityType.findProperty(attribute.getName()));
	}
}
