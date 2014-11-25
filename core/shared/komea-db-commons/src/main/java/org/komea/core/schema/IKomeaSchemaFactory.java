package org.komea.core.schema;

import org.komea.core.schema.IPrimitiveType.Primitive;

public interface IKomeaSchemaFactory {

	IReference newAttribute(String name, Primitive type, boolean isMany);

	IEntityType newEntity(String name);

	IPrimitiveType newPrimitive(Primitive primitive);

	IReference newReference(String name, IType type, boolean isMany,
			boolean isContainment);

	IKomeaSchema newSchema(String name);
}
