package org.komea.product.service.esper;

public interface IQueryValidation {
	/**
	 * Returns the error message.
	 * 
	 * @return the error message.
	 */
	String getErrorMessage();

	/**
	 * Tests if the query is valid;
	 * 
	 * @return true if the query is valid.
	 */
	boolean isValid();
}
