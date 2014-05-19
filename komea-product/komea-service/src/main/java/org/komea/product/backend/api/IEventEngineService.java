package org.komea.product.backend.api;

import java.io.Serializable;

import org.komea.eventory.api.engine.IQuery;
import org.komea.product.database.alert.IEvent;

/**
 */
public interface IEventEngineService {

	/**
	 * Registers a esper query.
	 * 
	 * @param _queryInformations
	 *            ICEPQueryImplementation
	 */
	void createOrUpdateQuery(IQueryInformations _queryInformations);

	/**
	 * Creates esper Statement.
	 * 
	 * @param _queryInformations
	 *            ICEPQueryImplementation
	 * @return the esper compiled statement.
	 */
	IQuery createQuery(IQueryInformations _queryInformations);

	/**
	 * Tests if a statement exist.
	 * 
	 * @param _statementKey
	 *            the statement key.
	 * @return boolean
	 */
	boolean existQuery(String _statementKey);

	/**
	 * Returns the given statement or null.
	 * 
	 * @param _statementName
	 * @return EPStatement
	 */
	<T extends IQuery> T getQuery(String _statementName);

	/**
	 * Returns the list of statement names.
	 * 
	 * @return the statement names.
	 */
	String[] getQueryNames();

	/**
	 * Returns the statement or throws an exception.
	 * 
	 * @param _statement
	 *            the statement name
	 * @return the esper statement
	 */
	<T extends IQuery> T getQueryOrFail(String _statement);

	/**
	 * Removes the query
	 * 
	 * @param _formula
	 *            the formula
	 */
	void removeQuery(String _formula);

	/**
	 * @param _customEvent
	 */
	void sendCustomEvent(Serializable _customEvent);

	/**
	 * Push alerts
	 * 
	 * @param _event
	 *            the event
	 */
	void sendEvent(IEvent _event);

}
