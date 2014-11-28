package org.komea.core.schema;

import java.util.List;

/**
 * A schema describing data types that can be stored in a Komea storage.
 * 
 * @author afloch
 *
 */
public interface IKomeaSchema {

	/**
	 * Add a type in the schema.
	 * 
	 * @param type
	 */
	void addType(IEntityType type);

	/**
	 * Get all types defined by the schema.
	 * 
	 * @return
	 */
	List<IEntityType> getTypes();

	/**
	 * Get the name of the schema
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Find a type from its name.
	 * 
	 * @param name
	 * @return
	 */
	IEntityType findType(String name);

}
