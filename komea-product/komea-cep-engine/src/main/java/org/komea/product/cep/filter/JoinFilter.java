/**
 * 
 */

package org.komea.product.cep.filter;



import java.io.Serializable;

import org.apache.commons.lang.Validate;
import org.komea.product.cep.api.IEventFilter;



/**
 * This class defines a filter that joins two filters.
 * 
 * @author sleroy
 */
public class JoinFilter extends AbstractEventFilter
{
    
    
    private final IEventFilter actionFilter;
    
    
    
    /**
     * Join filter.
     * 
     * @param _rootFilter
     *            the previous filter
     * @param _actionFilter
     *            the current filter action.
     */
    public JoinFilter(final IEventFilter _rootFilter, final IEventFilter _actionFilter) {
    
    
        super(_rootFilter);
        actionFilter = _actionFilter;
        Validate.notNull(_actionFilter);
        
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.filter.AbstractEventFilter#isEventFiltered(java.io.Serializable)
     */
    @Override
    protected final boolean isEventFiltered(final Serializable _event) {
    
    
        return actionFilter.isFiltered(_event);
    }
    
}
