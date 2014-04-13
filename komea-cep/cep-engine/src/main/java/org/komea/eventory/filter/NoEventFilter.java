/**
 * 
 */

package org.komea.eventory.filter;



import java.io.Serializable;

import org.komea.product.cep.api.IEventFilter;



/**
 * @author sleroy
 */
public class NoEventFilter implements IEventFilter<Serializable>
{
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.IEventFilter#isFiltered(java.lang.Object)
     */
    @Override
    public boolean isFiltered(final Serializable _event) {
    
    
        return _event != null;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "allow_all eventfilter";
    }
}
