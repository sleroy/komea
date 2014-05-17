/**
 * 
 */

package org.komea.eventory.api.bridge;

import org.komea.eventory.api.engine.ICEPEventListener;
import org.komea.eventory.api.engine.IQuery;

/**
 * This interface defines the CEP Engine.
 * 
 * @author sleroy
 */
public interface IEventBridge extends ICEPEventListener {

	/**
	 * Returns a query by its name;
	 * 
	 * @param _query
	 *            the query name.
	 * @return the query name.
	 */
	IQuery getQuery(String _query);

	/**
	 * Register a query.
	 * 
	 * @param _queryName
	 *            the query name.
	 * @param _query
	 *            the query.
	 */
	void registerQuery(String _queryName, IQuery _query);

	/**
	 * Remove the query
	 * 
	 * @param _queryName
	 *            the query name;
	 */
	void removeQuery(String _queryName);

}
