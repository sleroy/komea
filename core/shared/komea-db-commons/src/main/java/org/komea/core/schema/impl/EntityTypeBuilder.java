package org.komea.core.schema.impl;

import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.IKomeaSchemaFactory;
import org.komea.core.schema.Primitive;

public class EntityTypeBuilder {

	private final IKomeaSchemaFactory	schemaFactory;
	private final IEntityType	      entityType;
	private final IKomeaSchema	      schema;

	public EntityTypeBuilder(final IKomeaSchemaFactory _schemaFactory, final IKomeaSchema _schema,
	        final IEntityType _entityType) {
		this.schemaFactory = _schemaFactory;
		this.schema = _schema;
		this.entityType = _entityType;

	}

	public EntityTypeBuilder(final IKomeaSchemaFactory _schemaFactory, final IKomeaSchema _schema,
			final String _entityType) {
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

	public EntityTypeBuilder addProperty(final String _fieldName, final Primitive _primitiveType) {
		this.entityType.addProperty(this.schemaFactory.newAttribute(_fieldName, _primitiveType));
		return this;

	}

	public EntityTypeBuilder addStringProperty(final String _fieldName) {

		return this.addProperty(_fieldName, Primitive.STRING);

	}


	public EntityTypeBuilder setExtends(final IEntityType type){
		this.entityType.setSuperType(type);
		return this;
	}
	
	public IEntityType build() {
		this.schema.addType(this.entityType);
		return this.entityType;
	}

}
