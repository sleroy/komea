package org.komea.product.backend.api;

import java.io.Serializable;

import org.komea.eventory.api.engine.ICEPQuery;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dto.KpiResult;

/**
 */
public interface IEventEngineService {

	/**
	 * Registers a esper query.
	 * 
	 * @param _queryDefinition
	 *            ICEPQueryImplementation
	 */
	void createOrUpdateQuery(IQueryDefinition _queryDefinition);

	/**
	 * Creates esper Statement.
	 * 
	 * @param _queryDefinition
	 *            ICEPQueryImplementation
	 * @return the esper compiled statement.
	 */
	ICEPQuery createQuery(IQueryDefinition _queryDefinition);

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
	ICEPQuery getQuery(String _statementName);

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
	ICEPQuery<Serializable, KpiResult> getQueryOrFail(String _statement);

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
