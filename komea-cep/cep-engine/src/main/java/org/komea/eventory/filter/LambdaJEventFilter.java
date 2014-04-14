/**
 * 
 */

package org.komea.eventory.filter;



import java.io.Serializable;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.komea.eventory.api.filters.IEventFilter;



/**
 * Converts an even filter into a matcher.
 * 
 * @{link http://www.slideshare.net/shaiyallin/hamcrest-matchers}
 * @author sleroy
 */
public class LambdaJEventFilter<T extends Serializable> extends BaseMatcher<T>
{
    
    
    private final IEventFilter<T> filter;
    
    
    
    public LambdaJEventFilter(final IEventFilter<T> _filter) {
    
    
        super();
        filter = _filter;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
     */
    @Override
    public void describeTo(final Description _description) {
    
    
        _description.appendText("Event that matches the filter").appendValue(filter);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.hamcrest.Matcher#matches(java.lang.Object)
     */
    @Override
    public boolean matches(final Object _item) {
    
    
        return filter.isFiltered((T) _item);
    }
    
    
}
