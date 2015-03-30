package org.komea.core.schema.impl;

import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.IKomeaSchemaFactory;
import org.komea.core.schema.IReference;
import org.komea.core.schema.IType;
import org.komea.core.schema.Primitive;
import org.komea.core.schema.ReferenceKind;

public class KomeaSchemaFactory implements IKomeaSchemaFactory {

	@Override
	public IReference newAttribute(final String name, final Primitive type) {
		return new Reference(name, type).setKind(ReferenceKind.CONTAINMENT);
	}

	@Override
	public SchemaBuilder newBuilder(final String _name) {

		return new SchemaBuilder(_name);
	}

	@Override
	public IEntityType newEntity(final String name) {
		return new EntityType(name);
	}

	@Override
	public IReference newReference(final String name, final IType type) {
		return new Reference(name, type);
	}

	@Override
	public IKomeaSchema newSchema(final String name) {
		return new Schema(name);
	}

}
