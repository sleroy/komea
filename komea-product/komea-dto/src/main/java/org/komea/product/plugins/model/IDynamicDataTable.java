/**
 * 
 */

package org.komea.product.plugins.model;



import java.io.Serializable;
import java.util.List;

import org.komea.product.backend.utils.IFilter;



/**
 * @author sleroy
 */
public interface IDynamicDataTable<TData extends Serializable> extends Serializable
{
    
    
    /**
     * Returns the list of task.
     * 
     * @return the list of tasks.
     */
    List<TData> getData();
    
    
    /**
     * @return
     */
    boolean isEmpty();
    
    
    /**
     * Returns the list of task.
     * 
     * @return the list of tasks.
     */
    List<TData> searchData(IFilter<TData> _dataFilter);
}
