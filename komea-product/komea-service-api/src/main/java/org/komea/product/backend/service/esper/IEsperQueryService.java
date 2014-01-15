package org.komea.product.backend.service.esper;

/**
 * This interface defines a service to register statements and obtains the list
 * of statements (and names) from esper service.
 * 
 * @author sleroy
 * 
 */
public interface IEsperQueryService {

	/**
	 * Returns the list of statement names.
	 * 
	 * @return the statement names.
	 */
	String[] getStatementNames();

	/**
	 * Registers a esper query.
	 * 
	 * @param _query
	 *            the query.
	 * @param _statementName
	 *            the statement name.
	 */
	void registerQuery(String _query, String _statementName);

}
