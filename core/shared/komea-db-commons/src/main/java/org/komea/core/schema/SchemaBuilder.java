package org.komea.core.schema;

import org.komea.core.schema.impl.KomeaSchemaFactory;

/**
 * This class is a builder to create easily an schema.
 * 
 * @author sleroy
 *
 */
public class SchemaBuilder {

	private final KomeaSchemaFactory	schemaFactory	= new KomeaSchemaFactory();
	private final IKomeaSchema	     schema;

	public SchemaBuilder(final IKomeaSchema _schema) {
		this.schema = _schema;
	}

	public SchemaBuilder(final String _schema) {
		this.schema = this.schemaFactory.newSchema(_schema);
	}

	public IKomeaSchema build() {

		return this.schema;
	}

	public void entity_contains_many(final String _fieldName, final IEntityType _entity, final IEntityType _entity2) {
		_entity.addProperty(this.schemaFactory.newManyContainmentReference(_fieldName, _entity2));

	}

	public void entity_refers_many(final String _fieldName, final IEntityType _entity, final IEntityType _entity2) {
		_entity.addProperty(this.schemaFactory.newManyReference(_fieldName, _entity2));

	}

	public KomeaSchemaFactory getSchemaFactory() {
		return this.schemaFactory;
	}

	public EntityTypeBuilder newEntity(final String _entityType) {

		return new EntityTypeBuilder(this.schemaFactory, this.schema, _entityType);
	}

}
