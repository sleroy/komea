/**
 * 
 */

package org.komea.product.cep.api;



import org.komea.product.cep.api.cache.ICacheConfiguration;



/**
 * This interface defines a filter used by a CEP Query
 * 
 * @author sleroy
 */
public interface IFilterDefinition
{
    
    
    /**
     * @return
     */
    ICacheConfiguration getCacheConfiguration();
    
    
    /**
     * Returns the event transformer or null.
     */
    IEventTransformer getEventTransformer();
    
    
    /**
     * @return
     */
    IEventFilter getFilter();
    
    
    /**
     * @return
     */
    String getFilterName();
    
    
}
