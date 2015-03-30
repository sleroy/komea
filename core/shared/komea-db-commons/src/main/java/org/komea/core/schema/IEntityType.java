package org.komea.core.schema;

import java.util.List;

/**
 * A simple data structure type used by Komea to store various informations in a
 * storage.
 * 
 * @author afloch
 *
 */
public interface IEntityType extends IType {

	/**
	 * Get the schema that declare the entity type.
	 * 
	 * @return
	 */
	IKomeaSchema getSchema();

	/**
	 * Add a reference property to this type.
	 * 
	 * @param reference
	 */
	void addProperty(IReference reference);

	/**
	 * Find a reference property from its name. It looks in inheritance
	 * ascendancy if property does'nt exists in the type.
	 * 
	 * @param name
	 * @return
	 */
	IReference findProperty(String name);

	/**
	 * Get properties defined in the type.
	 * 
	 * @return
	 */
	List<IReference> getProperties();

	/**
	 * Get the properties defined in the type and those defined in inheritance
	 * ascendancy.
	 * 
	 * @return
	 */
	List<IReference> getAllProperties();

	/**
	 * Get the inherited super type.
	 * 
	 * @return
	 */
	IEntityType getSuperType();

	/**
	 * Set the inherited super type.
	 * 
	 * @param type
	 */
	void setSuperType(IEntityType type);
}
