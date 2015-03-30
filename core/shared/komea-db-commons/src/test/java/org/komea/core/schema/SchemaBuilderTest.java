package org.komea.core.schema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.core.schema.impl.EntityType;
import org.komea.core.schema.impl.EntityTypeBuilder;
import org.komea.core.schema.impl.SchemaBuilder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SchemaBuilderTest {
	@Mock
	private IKomeaSchema	schema;

	@Test
	public final void testEntityAggregatesMany() throws Exception {
		final SchemaBuilder schemaBuilder = new SchemaBuilder("example");
		final IEntityType entity1 = new EntityType("e1");
		final IEntityType entity2 = new EntityType("e2");
		final String fieldReference = "field1";
		schemaBuilder.entityAggregatesMany(fieldReference, entity1, entity2);
		assertEquals(entity1.getAllProperties().get(0).getKind(), ReferenceKind.AGGREGATION);
	}

	@Test
	public final void testEntityContainsMany() throws Exception {
		final SchemaBuilder schemaBuilder = new SchemaBuilder("example");
		final IEntityType entity1 = new EntityType("e1");
		final IEntityType entity2 = new EntityType("e1");
		final String fieldReference = "field1";
		schemaBuilder.entityContainsMany(fieldReference, entity1, entity2);
		assertEquals(entity1.getAllProperties().get(0).getKind(), ReferenceKind.CONTAINMENT);
	}

	@Test
	public final void testGetSchemaFactory() throws Exception {
		assertNotNull(new SchemaBuilder("schema").getSchemaFactory());
	}

	@Test
	public final void testNewEntity() throws Exception {
		final String entityType = "Human";
		final EntityTypeBuilder entity = new SchemaBuilder("test").newEntity(entityType);
		assertNotNull("Cannot be null, factory", entity);
		assertEquals("Entity type should be properly initialized", entity.build().getName(), entityType);
	}

	@Test
	public final void testSchemaBuilderIKomeaSchema() throws Exception {
		when(this.schema.getName()).thenReturn("ExampleSchema");
		assertEquals("Same name", new SchemaBuilder(this.schema).build().getName(), this.schema.getName());
		assertEquals("Same instance", new SchemaBuilder(this.schema).build(), this.schema);
	}

	@Test
	public final void testSchemaBuilderString() throws Exception {
		final String schemaName = "exampleSchema";
		assertEquals(new SchemaBuilder(schemaName).build().getName(), schemaName);
	}

}
