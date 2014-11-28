package org.komea.core.schema;

import org.komea.core.schema.IPrimitiveType.Primitive;
import org.komea.core.schema.impl.KomeaSchemaFactory;

public class EntityTypeBuilder {

	private final KomeaSchemaFactory schemaFactory;
	private final IEntityType entityType;
	private final IKomeaSchema schema;

	public EntityTypeBuilder(final KomeaSchemaFactory _schemaFactory,
			final IKomeaSchema _schema, final String _entityType) {
		this.schemaFactory = _schemaFactory;
		this.schema = _schema;
		this.entityType = this.schemaFactory.newEntity(_entityType);

	}

	public EntityTypeBuilder addBooleanProperty(final String _fieldName) {

		return this.addProperty(_fieldName, Primitive.BOOLEAN);

	}

	public EntityTypeBuilder addDateProperty(final String _fieldName) {

		return this.addProperty(_fieldName, Primitive.DATE);

	}

	public EntityTypeBuilder addDoubleProperty(final String _fieldName) {

		return this.addProperty(_fieldName, Primitive.DOUBLE);

	}

	public EntityTypeBuilder addIntegerProperty(final String _fieldName) {

		return this.addProperty(_fieldName, Primitive.INTEGER);

	}

	public EntityTypeBuilder addProperty(final String _fieldName,
			final Primitive _date) {
		this.entityType.addProperty(this.schemaFactory.newReference(_fieldName,
				this.schemaFactory.newPrimitive(_date)));
		return this;

	}

	public EntityTypeBuilder addStringProperty(final String _fieldName) {

		return this.addProperty(_fieldName, Primitive.STRING);

	}

	public IEntityType build() {
		this.schema.addType(this.entityType);
		return this.entityType;
	}

}
