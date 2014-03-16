/**
 * 
 */

package org.komea.product.plugins.kpi.filters;



import org.komea.product.cep.api.IEventFilter;
import org.komea.product.database.alert.IEvent;



/**
 * This class is a filter on event type;
 * 
 * @author sleroy
 */
public class EventTypeFilter implements IEventFilter<IEvent>
{
    
    
    private final String eventKey;
    
    
    
    public EventTypeFilter(final String _eventKey) {
    
    
        eventKey = _eventKey;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.IEventFilter#isFiltered(java.io.Serializable)
     */
    @Override
    public boolean isFiltered(final IEvent _event) {
    
    
        return eventKey.equals(_event.getEventType().getEventKey());
    }
    
}
