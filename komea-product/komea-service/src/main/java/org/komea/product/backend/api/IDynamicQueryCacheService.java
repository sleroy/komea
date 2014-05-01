
package org.komea.product.backend.api;



import java.util.List;

import org.komea.cep.dynamicdata.IDynamicDataQuery;
import org.komea.product.database.dto.KpiResult;



/**
 * This interface defines the service to manipulate the cache query.
 * 
 * @author sleroy
 */
public interface IDynamicQueryCacheService
{
    
    
    /**
     * Add a cache on a dynamic query.
     * 
     * @param dynamicDataQuery
     *            the dynamic query
     * @return the dynamic queyr with the cache.
     */
    IDynamicDataQuery addCacheOnDynamicQuery(
            String _dynamicDataQuery,
            final IDynamicDataQuery dynamicDataQuery);
    
    
    /**
     * Returns the stored query names.
     * 
     * @return the stored query names
     */
    List<String> getStoredQueryNames();
    
    
    /**
     * Refresht the value of a query into the cache.
     * 
     * @param _queryKey
     *            the query
     * @param _result
     *            the new value.
     */
    void refreshValue(String _queryKey, KpiResult _result);
    
    
}
