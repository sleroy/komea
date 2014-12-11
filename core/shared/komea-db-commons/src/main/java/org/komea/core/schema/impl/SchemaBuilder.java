package org.komea.core.schema.impl;

import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.ReferenceArity;
import org.komea.core.schema.ReferenceKind;

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

	public void entityContainsMany(final String _fieldName, final IEntityType _entity, final IEntityType _entity2) {
		_entity.addProperty(this.schemaFactory.newReference(_fieldName, _entity2).setKind(ReferenceKind.CONTAINMENT)
		        .setArity(ReferenceArity.MANY));

	}

	public void entityAggregatesMany(final String _fieldName, final IEntityType _entity, final IEntityType _entity2) {
		_entity.addProperty(this.schemaFactory.newReference(_fieldName, _entity2).setKind(ReferenceKind.AGGREGATION)
		        .setArity(ReferenceArity.MANY));

	}
	public void entityContainsOne(final String _fieldName, final IEntityType _entity, final IEntityType _entity2) {
		_entity.addProperty(this.schemaFactory.newReference(_fieldName, _entity2).setKind(ReferenceKind.CONTAINMENT)
		        .setArity(ReferenceArity.ONE));

	}

	public void entityAggregatesOne(final String _fieldName, final IEntityType _entity, final IEntityType _entity2) {
		_entity.addProperty(this.schemaFactory.newReference(_fieldName, _entity2).setKind(ReferenceKind.AGGREGATION)
		        .setArity(ReferenceArity.ONE));

	}

	public KomeaSchemaFactory getSchemaFactory() {
		return this.schemaFactory;
	}

	public EntityTypeBuilder newEntity(final String _entityType) {

		return new EntityTypeBuilder(this.schemaFactory, this.schema, _entityType);
	}

}
