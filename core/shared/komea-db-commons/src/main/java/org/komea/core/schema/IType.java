package org.komea.core.schema;

/**
 * Type definition.
 * 
 * @author afloch
 *
 */
public interface IType {
	/**
	 * Get the type name.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Test if the type is for a primitive data.
	 * 
	 * @return true if type is primitive, false if type is an entity type.
	 */
	boolean isPrimitive();
}
