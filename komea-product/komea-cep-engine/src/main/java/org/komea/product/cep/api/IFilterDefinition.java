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
     * @return the cache configuration (cannot be null)
     */
    ICacheConfiguration getCacheConfiguration();
    
    
    /**
     * @return the event filter (cannot be null)
     */
    IEventFilter getEventFilter();
    
    
    /**
     * @return the event transformer or null
     */
    IEventTransformer getEventTransformer();
    
    
    /**
     * Returns the filter name
     */
    String getFilterName();
    
}
