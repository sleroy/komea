package org.komea.core.schema.impl;

import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.IKomeaSchemaFactory;
import org.komea.core.schema.IPrimitiveType;
import org.komea.core.schema.IPrimitiveType.Primitive;
import org.komea.core.schema.IReference;
import org.komea.core.schema.IType;

public class KomeaSchemaFactory implements IKomeaSchemaFactory {

	@Override
	public IReference newAttribute(final String name, final Primitive type,
			final boolean isMany) {
		return new Reference(name, newPrimitive(type), isMany, true);
	}

	@Override
	public IEntityType newEntity(final String name) {
		return new EntityType(name);
	}

	@Override
	public IPrimitiveType newPrimitive(final Primitive primitive) {
		return new PrimitiveType(primitive);
	}

	@Override
	public IReference newReference(final String name, final IType type,
			final boolean isMany, final boolean isContainment) {
		return new Reference(name, type, isMany, isContainment);
	}

	@Override
	public IKomeaSchema newSchema(final String name) {
		return new Schema(name);
	}

}
