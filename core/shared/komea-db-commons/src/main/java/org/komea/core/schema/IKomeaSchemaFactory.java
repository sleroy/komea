package org.komea.core.schema;


/**
 * Factory to create types and references that will be stored in a schema.
 *
 * @author afloch
 *
 */
public interface IKomeaSchemaFactory {

	IReference newAttribute(String name, Primitive type);

	SchemaBuilder newBuilder(String name);

	IReference newContainmentReference(String _name, IType _type);

	IEntityType newEntity(String name);

	IReference newManyContainmentReference(String _name, IType _type);

	IReference newManyReference(String _name, IType _type);

	IReference newReference(String name, IType type);

	IKomeaSchema newSchema(String name);
}
