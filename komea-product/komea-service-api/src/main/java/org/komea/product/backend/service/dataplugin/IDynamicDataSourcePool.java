/**
 * 
 */

package org.komea.product.backend.service.dataplugin;



import java.util.Set;

import org.komea.eventory.api.cache.ICacheConfiguration;
import org.komea.product.plugins.model.IDynamicDataSource;
import org.komea.product.plugins.model.IDynamicDataSourceSession;



/**
 * @author sleroy
 */
public interface IDynamicDataSourcePool
{
    
    
    /**
     * Returns the list of data sources registered
     * 
     * @return the list of data sources registered.
     */
    Set<String> getDataSources();
    
    
    /**
     * @return Returns true if for the given data source, the data is stored in cache.
     */
    boolean isDataInCache(String _dataSourceName);
    
    
    /**
     * Register a dynamic data source and returns a session on it.
     * 
     * @param _sourcePool
     *            the source
     * @return the session.
     */
    IDynamicDataSourceSession register(
            String _dataSourceName,
            IDynamicDataSource _sourcePool,
            ICacheConfiguration _cacheConfiguration);
}
