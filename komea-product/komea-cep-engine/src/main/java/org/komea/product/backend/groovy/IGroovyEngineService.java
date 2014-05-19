package org.komea.product.backend.groovy;

public interface IGroovyEngineService {

	/**
	 * Parses the formula into a query.
	 * 
	 * @param _formula
	 *            the formula
	 * @return true if the formula is valid.
	 */
	public boolean isValidFormula(final String _formula);

	/**
	 * Parses a groovy script
	 * 
	 * @param _groovyScript
	 * @return the parsed groovy script.
	 */
	public <T> T parseGroovyScript(String _groovyScript);

}