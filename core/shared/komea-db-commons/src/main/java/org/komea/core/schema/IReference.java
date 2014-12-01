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
	 * Get the {@link ReferenceKind} of the reference.
	 * 
	 * @return
	 */
	ReferenceKind getKind();

	/**
	 * Get the {@link ReferenceArity} of the reference.
	 * 
	 * @return
	 */
	ReferenceArity getArity();
	
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
	 * Test if the reference is unique.
	 * 
	 * @return
	 */
	boolean isUnique();


	/**
	 * Test if the reference is indexed.
	 * 
	 * @return
	 */
	boolean isIndexed();

	
	/**
	 * Test if the reference instance can store multiple values.
	 * 
	 * @return
	 */
	boolean isMany();

	/**
	 * Set the reference kind property (e.g., Containment). It returns the updated reference
	 * for a fluent initialization.
	 * 
	 * @param kind
	 * @return the updated reference
	 */
	IReference setKind(ReferenceKind kind);
	
	/**
	 * Set the reference arity property (One or Many). It returns the updated reference
	 * for a fluent initialization.
	 * 
	 * @param kind
	 * @return the updated reference
	 */
	IReference setArity(ReferenceArity arity);

	
	
	/**
	 * Set the mandatory property of the reference. It returns the updated reference
	 * for a fluent initialization.
	 * 
	 * @return the updated reference
	 */
	IReference enableMandatory();


	/**
	 * Unset the mandatory property of the reference. It returns the updated reference
	 * for a fluent initialization.
	 * 
	 * @return the updated reference
	 */
	IReference disableMandatory();
	
	/**
	 * Enable the indexation of the reference. It returns the updated reference
	 * for a fluent initialization.
	 * 
	 * @return the updated reference
	 */
	IReference enableIndexation();


	/**
	 * Disable the indexation of the reference. It returns the updated reference
	 * for a fluent initialization.
	 * 
	 * @return the updated reference
	 */
	IReference disableIndexation();
	
	
	/**
	 * Set the unique property of the reference. It returns the updated reference
	 * for a fluent initialization.
	 * 
	 * @return the updated reference
	 */
	IReference enableUnique();


	/**
	 * Unset the unique property of the reference. It returns the updated reference
	 * for a fluent initialization.
	 * 
	 * @return the updated reference
	 */
	IReference disableUnique();
	
}
