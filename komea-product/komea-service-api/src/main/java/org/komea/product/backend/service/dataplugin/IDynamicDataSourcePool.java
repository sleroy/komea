/**
 * 
 */

package org.komea.product.backend.service.dataplugin;



import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.komea.product.plugins.model.IDynamicDataSourceSession;



/**
 * @author sleroy
 */
public interface IDynamicDataSourcePool
{
    
    
    /**
     * Returns the data source name
     * 
     * @param _dataSourceName
     *            the data source name
     * @return a session to obtain dynamic data source
     */
    <T extends IDynamicDataSourceSession> T getDataSource(String _dataSourceName);
    
    
    /**
     * Returns directly the data from a data source.
     * 
     * @param _dataSourceName
     *            the data source name
     * @return the data source data.
     */
    <T extends Serializable> T getDataSourceData(String _dataSourceName);
    
    
    /**
     * Get data source of type.
     * 
     * @param _class
     *            the class
     * @return the data source data.
     */
    <T> Collection<T> getDataSourceOfType(Class<T> _class);
    
    
    /**
     * Returns the list of data sources registered
     * 
     * @return the list of data sources registered.
     */
    Set<String> getDataSources();
    
    
    /**
     * @return Returns true if for the given data source, the data is stored in cache.
     */
    boolean isDataInCache(String _dataSource);
    
}
