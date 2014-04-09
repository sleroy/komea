/**
 * 
 */

package org.komea.product.cep.filter;



import java.io.Serializable;

import org.komea.product.cep.api.IEventFilter;



/**
 * @author sleroy
 */
public class NoEventFilter implements IEventFilter<Serializable>
{
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.IEventFilter#isFiltered(java.lang.Object)
     */
    @Override
    public boolean isFiltered(final Serializable _event) {
    
    
        return _event != null;
    }
    
}
