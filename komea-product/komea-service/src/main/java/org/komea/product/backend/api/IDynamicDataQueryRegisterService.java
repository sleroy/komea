/**
 * 
 */

package org.komea.product.backend.api;

import java.util.Iterator;
import java.util.List;

import org.komea.product.cep.api.dynamicdata.IDynamicDataQuery;

/**
 * @author sleroy
 */
public interface IDynamicDataQueryRegisterService {

	/**
	 * Tests if the query exist
	 * 
	 * @param _engineKey
	 *            the query key.
	 * @return true if the query is in the query.
	 */
	public boolean existQuery(String _engineKey);

	/**
	 * Returns the list of queries into the registry.
	 * 
	 * @return the list of queries
	 */
	public Iterator<IDynamicDataQuery> getQueriesIterator();

	/**
	 * Returns the query
	 * 
	 * @param _queryKey
	 *            the query key
	 * @return the dynamic query
	 */
	public IDynamicDataQuery getQuery(String _queryKey);

	/**
	 * Returns the query names
	 * 
	 * @return the query names;
	 */
	public List<String> getQueryNames();

	/**
	 * Registers the query
	 * 
	 * @param _queryName
	 *            the query name
	 * @param _query
	 *            the query.
	 */
	public void registerQuery(String _queryName, IDynamicDataQuery _query);

	/**
	 * Removes the query
	 * 
	 * @param _queryName
	 *            the query name
	 * @return the query name;
	 */
	public boolean removeQuery(String _queryName);

}
