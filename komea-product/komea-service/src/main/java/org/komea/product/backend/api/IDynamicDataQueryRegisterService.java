/**
 * 
 */

package org.komea.product.backend.api;



import java.util.Iterator;
import java.util.List;

import org.komea.cep.dynamicdata.IDynamicDataQuery;



/**
 * @author sleroy
 */
public interface IDynamicDataQueryRegisterService
{
    
    
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
    
    
    public void registerQuery(String _queryName, IDynamicDataQuery _query);
    
    
    public boolean removeQuery(String _queryName);
    
}
