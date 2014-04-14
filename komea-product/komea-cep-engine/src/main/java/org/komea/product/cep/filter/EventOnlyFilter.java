/**
 * 
 */

package org.komea.product.cep.filter;



import java.io.Serializable;

import org.komea.eventory.api.filters.IEventFilter;
import org.komea.product.database.alert.IEvent;



/**
 * This class defines a filter that only accepts IEvent classes
 * 
 * @author sleroy
 */
public class EventOnlyFilter implements IEventFilter
{
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.IEventFilter#isFiltered(java.io.Serializable)
     */
    @Override
    public boolean isFiltered(final Serializable _event) {
    
    
        return _event instanceof IEvent;
    }
    
}
