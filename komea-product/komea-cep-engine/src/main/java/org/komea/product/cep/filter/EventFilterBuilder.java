/**
 * 
 */

package org.komea.product.cep.filter;



import org.komea.product.cep.api.IEventFilter;



;


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
    
    
    
    private IEventFilter<?> filter = new NoEventFilter();
    
    
    
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
    
    
    public EventFilterBuilder chain(final IEventFilter<?> _filter) {
    
    
        filter = new JoinFilter(filter, _filter);
        return this;
    }
    
    
    public EventFilterBuilder onlyIEvents() {
    
    
        filter = new JoinFilter(filter, new EventOnlyFilter());
        return this;
    }
}
