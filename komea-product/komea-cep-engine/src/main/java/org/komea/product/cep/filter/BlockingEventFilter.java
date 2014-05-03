/**
 * 
 */

package org.komea.product.cep.filter;



import org.komea.eventory.api.filters.IEventFilter;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.enums.Severity;



/**
 * @author sleroy
 */
public class BlockingEventFilter implements IEventFilter<IEvent>
{
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.IEventFilter#isFiltered(org.komea.product.database.alert.Event)
     */
    @Override
    public boolean isFiltered(final IEvent _event) {
    
    
        return _event.getEventType().getSeverity() == Severity.BLOCKER;
    }
    
}
