package org.komea.core.schema;

import org.komea.core.schema.impl.SchemaBuilder;

/**
 * Factory to create types and references that will be stored in a schema.
 *
 * @author afloch
 *
 */
public interface IKomeaSchemaFactory {

	IReference newAttribute(String name, Primitive type);

	SchemaBuilder newBuilder(String name);

	IEntityType newEntity(String name);

	IReference newReference(String name, IType type);

	IKomeaSchema newSchema(String name);
}
