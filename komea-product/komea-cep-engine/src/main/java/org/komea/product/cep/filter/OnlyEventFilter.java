/**
 * 
 */

package org.komea.product.cep.filter;



import java.io.Serializable;

import org.komea.eventory.api.filters.IEventFilter;
import org.komea.product.database.alert.IEvent;



/**
 * @author sleroy
 */
public class OnlyEventFilter implements IEventFilter<Serializable>
{
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.IEventFilter#isFiltered(java.io.Serializable)
     */
    @Override
    public boolean isFiltered(final Serializable _arg0) {
    
    
        return _arg0 instanceof IEvent;
    }
    
    
}
