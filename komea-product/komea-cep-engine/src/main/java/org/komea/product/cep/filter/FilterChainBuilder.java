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
public class FilterChainBuilder
{
    
    
    /**
     * Creates a new filter chain builder.
     * 
     * @return the instance
     */
    public static FilterChainBuilder create() {
    
    
        return new FilterChainBuilder();
    }
    
    
    
    private IEventFilter<?> filter = new NoEventFilter();
    
    
    
    private FilterChainBuilder() {
    
    
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
    
    
    public FilterChainBuilder chain(final IEventFilter<?> _filter) {
    
    
        filter = new JoinFilter(filter, _filter);
        return this;
    }
    
    
    public FilterChainBuilder onlyIEvents() {
    
    
        filter = new JoinFilter(filter, new EventOnlyFilter());
        return this;
    }
}
