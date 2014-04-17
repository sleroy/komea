/**
 * 
 */

package org.komea.product.cep.filter;



import org.komea.eventory.api.filters.IEventFilter;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.enums.Severity;



/**
 * Event criticity filter.
 * 
 * @author sleroy
 */
public class EventSeverityFilter implements IEventFilter<IEvent>
{
    
    
    private final Severity severity;
    
    
    
    public EventSeverityFilter(final Severity _severity) {
    
    
        severity = _severity;
        
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.IEventFilter#isFiltered(java.io.Serializable)
     */
    @Override
    public boolean isFiltered(final IEvent _event) {
    
    
        return _event.getEventType() != null && _event.getEventType().getSeverity() == severity;
        
    }
}
