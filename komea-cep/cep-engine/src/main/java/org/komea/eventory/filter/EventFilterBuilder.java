/**
 * 
 */

package org.komea.eventory.filter;



import org.komea.eventory.query.FilterDefinition;
import org.komea.product.cep.api.IEventFilter;
import org.komea.product.cep.api.IFilterDefinition;
import org.komea.product.cep.api.cache.ICacheConfiguration;



/**
 * This class is a helper to build a filter chain.
 * 
 * @author sleroy
 */
public class EventFilterBuilder
{
    
    
    /**
     * Creates a new filter chain builder.
     * 
     * @return the instance
     */
    public static EventFilterBuilder create() {
    
    
        return new EventFilterBuilder();
    }
    
    
    
    private IEventFilter filter = new NoEventFilter();
    
    
    
    private EventFilterBuilder() {
    
    
        super();
    }
    
    
    /**
     * This class returns a filter;
     * 
     * @return the filter to build;
     */
    public IEventFilter<?> build() {
    
    
        return filter;
    }
    
    
    /**
     * Returns a filter definition.
     * 
     * @return the filter definition.
     */
    public IFilterDefinition buildFilterDefinition(
            final String _filterName,
            final ICacheConfiguration _cacheConfiguration) {
    
    
        return new FilterDefinition(_filterName, filter, _cacheConfiguration);
    }
    
    
    public EventFilterBuilder chain(final IEventFilter _filter) {
    
    
        filter = FilterOperator.and(filter, _filter);
        return this;
        
    }
    
    
}
