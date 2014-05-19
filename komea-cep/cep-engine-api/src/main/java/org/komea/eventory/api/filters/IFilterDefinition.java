/**
 * 
 */

package org.komea.eventory.api.filters;



import org.komea.eventory.api.cache.ICacheConfiguration;



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
    
    
    IEventFilter getFilter();
    
    
    /**
     * @return
     */
    String getFilterName();
    
    
}
