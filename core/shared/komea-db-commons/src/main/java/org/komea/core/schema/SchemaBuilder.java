package org.komea.core.schema;

import org.komea.core.schema.impl.KomeaSchemaFactory;

public class SchemaBuilder {

	private final KomeaSchemaFactory schemaFactory = new KomeaSchemaFactory();
	private final IKomeaSchema schema;

	public SchemaBuilder(final String _schema) {
		this.schema = this.schemaFactory.newSchema(_schema);
	}

	public IKomeaSchema build() {

		return this.schema;
	}

	public EntityTypeBuilder newEntity(final String _entityType) {

		return new EntityTypeBuilder(this.schemaFactory, this.schema,
				_entityType);
	}

}
