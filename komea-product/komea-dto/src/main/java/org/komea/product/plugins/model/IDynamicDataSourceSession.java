/**
 * 
 */

package org.komea.product.plugins.model;



/**
 * @author sleroy
 */
public interface IDynamicDataSourceSession<T> extends IDynamicDataSource<T>
{
    
    
    String getDataSourceName();
    
    
    /**
     * Returns true if data is available in cache
     * 
     * @return true if the data is available in cache
     */
    boolean isDataAvailableInCache();
    
}
