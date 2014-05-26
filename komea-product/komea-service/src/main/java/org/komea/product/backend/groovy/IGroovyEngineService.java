package org.komea.product.backend.groovy;

import groovy.lang.Script;

import org.komea.eventory.api.engine.IQuery;

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
	public <T> Class<T> parseClass(String _groovyScript);

	/**
	 * Parses a query
	 * 
	 * @param _groovyScript
	 * @return the instantiated query
	 */
	public <T extends IQuery> T parseQuery(String _groovyScript);

	/**
	 * Returns a script object parsed from a source.
	 * 
	 * @param _groovyScript
	 *            the groovy script text
	 * @return the script
	 */
	Script parseScript(String _groovyScript);

}