package org.komea.core.schema;

/**
 * Typed data field definition of an {@link IEntityType}. Reference support
 * following UML features : arity, aggregation, containment.
 * 
 * @author afloch
 *
 */
public interface IReference {
	/**
	 * Get the reference name.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Get the referenced type.
	 * 
	 * @return
	 */
	IType getType();

	/**
	 * Test if the reference is a containment.
	 * 
	 * @return
	 */
	boolean isContainment();

	/**
	 * Test if the reference is an aggregation.
	 * 
	 * @return
	 */
	boolean isAggregation();

	/**
	 * Test if the reference is mandatory.
	 * 
	 * @return
	 */
	boolean isMandatory();

	/**
	 * Test if the reference instance can store multiple values.
	 * 
	 * @return
	 */
	boolean isMany();

	/**
	 * Set the reference containment property. It returns the updated reference
	 * for a fluent initialization.
	 * 
	 * @param containment
	 * @return the updated reference
	 */
	IReference setContainment(boolean containment);

	/**
	 * Set the reference mandatory property. It returns the updated reference
	 * for a fluent initialization.
	 * 
	 * @param mandatory
	 * @return the updated reference
	 */
	IReference setMandatory(boolean mandatory);

	/**
	 * Set the reference mant property. It returns the updated reference for a
	 * fluent initialization.
	 * 
	 * @param many
	 * @return the updated reference
	 */
	IReference setMany(boolean many);

	/**
	 * Set the reference aggregation property. It returns the updated reference
	 * for a fluent initialization.
	 * 
	 * @param aggregation
	 * @return the updated reference
	 */
	IReference setAggregation(boolean aggregation);
}
