package org.komea.core.schema;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.core.schema.impl.EntityType;
import org.komea.core.schema.impl.EntityTypeBuilder;
import org.komea.core.schema.impl.KomeaSchemaFactory;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EntityTypeBuilderTest {

	@Mock
	private IKomeaSchema	          schema;

	private final IKomeaSchemaFactory	schemaFactory	= new KomeaSchemaFactory();

	private IEntityType	              fakeEntityType	= null;

	@Test
	public final void testAddBooleanProperty() throws Exception {
		this.fakeEntityType = new EntityType("Human");
		final EntityTypeBuilder entityTypeBuilder = new EntityTypeBuilder(this.schemaFactory, this.schema,
				this.fakeEntityType);

		entityTypeBuilder.addBooleanProperty("gni");

		this.validatePrimitiveType(Primitive.BOOLEAN);
	}

	@Test
	public final void testAddContainmentReferenceProperty() throws Exception {
		// TODO
		// throw new RuntimeException("not yet implemented");
	}

	@Test
	public final void testAddDateProperty() throws Exception {
		this.fakeEntityType = new EntityType("Human");
		final EntityTypeBuilder entityTypeBuilder = new EntityTypeBuilder(this.schemaFactory, this.schema,
				this.fakeEntityType);
		entityTypeBuilder.addDateProperty("gni");

		this.validatePrimitiveType(Primitive.DATE);
	}

	@Test
	public final void testAddDoubleProperty() throws Exception {
		this.fakeEntityType = new EntityType("Human");
		final EntityTypeBuilder entityTypeBuilder = new EntityTypeBuilder(this.schemaFactory, this.schema,
				this.fakeEntityType);
		entityTypeBuilder.addDoubleProperty("gni");

		this.validatePrimitiveType(Primitive.DOUBLE);
	}

	@Test
	public final void testAddIntegerProperty() throws Exception {
		this.fakeEntityType = new EntityType("Human");
		final EntityTypeBuilder entityTypeBuilder = new EntityTypeBuilder(this.schemaFactory, this.schema,
				this.fakeEntityType);
		entityTypeBuilder.addIntegerProperty("gni");

		this.validatePrimitiveType(Primitive.INTEGER);
	}

	@Test
	public final void testAddManyContainmentReferenceProperty() throws Exception {
		// TODO
		// throw new RuntimeException("not yet implemented");
	}

	@Test
	public final void testAddManyReferenceProperty() throws Exception {
		// TODO
		// throw new RuntimeException("not yet implemented");
	}

	@Test
	public final void testAddProperty() throws Exception {
		// TODO
		// throw new RuntimeException("not yet implemented");
	}

	@Test
	public final void testAddStringProperty() throws Exception {
		this.fakeEntityType = new EntityType("Human");
		final EntityTypeBuilder entityTypeBuilder = new EntityTypeBuilder(this.schemaFactory, this.schema,
				this.fakeEntityType);
		entityTypeBuilder.addStringProperty("gni");

		this.validatePrimitiveType(Primitive.STRING);
	}

	private void validatePrimitiveType(final Primitive _primitiveType) {
		assertEquals("Property is added", 1, this.fakeEntityType.getProperties().size());
		assertEquals("Property is a containment", ReferenceKind.CONTAINMENT, this.fakeEntityType.getProperties().get(0)
				.getKind());
		assertEquals("Property is arity one", ReferenceArity.ONE, this.fakeEntityType.getProperties().get(0).getArity());
		final IType type = this.fakeEntityType.getProperties().get(0).getType();

		assertEquals("Property is a primitive type", _primitiveType, type);
	}
}
