/**
 * 
 */

package org.komea.product.plugins.model;

import java.io.Serializable;



/**
 * @author sleroy
 */
public interface IDynamicDataSourceSession<T extends Serializable> extends IDynamicDataSource<T>
{
    
    
    String getDataSourceName();
    
    
    /**
     * Returns true if data is available in cache
     * 
     * @return true if the data is available in cache
     */
    boolean isDataAvailableInCache();
    
}
