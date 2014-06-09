/**
 * 
 */

package org.komea.product.plugins.model;



import java.io.Serializable;



/**
 * @author sleroy
 * @param <T>
 *            the type of data
 */
public interface IDynamicDataSource<T extends Serializable>
{
    
    
    /**
     * Returns the data
     * 
     * @return the data
     */
    T fetchData();
    
    
    /**
     * Returns the class /interface returned by the dynamic data source.
     * 
     * @return the definition.
     */
    Class<T> getDefinition();
    
}
