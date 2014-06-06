/**
 * 
 */

package org.komea.product.plugins.bugtracker.kpis;



import groovy.lang.Closure;

import org.komea.product.backend.utils.IFilter;
import org.komea.product.plugins.bugtracking.model.IIssue;



/**
 * @author sleroy
 */
public class ClosureFilter implements IFilter<IIssue>
{
    
    
    private final Closure<Boolean> filter;
    
    
    
    /**
     * @param _filter
     */
    public ClosureFilter(final Closure<Boolean> _filter) {
    
    
        super();
        filter = _filter;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.utils.IFilter#matches(java.lang.Object)
     */
    @Override
    public boolean matches(final IIssue _task) {
    
    
        return filter.call(_task);
    }
    
}
