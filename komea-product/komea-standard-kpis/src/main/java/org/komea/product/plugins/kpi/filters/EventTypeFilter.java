/**
 * 
 */

package org.komea.product.plugins.kpi.filters;



import java.util.HashSet;
import java.util.Set;

import org.komea.eventory.api.filters.IEventFilter;
import org.komea.product.database.alert.IEvent;



/**
 * This class is a filter on event type;
 * 
 * @author sleroy
 */
public class EventTypeFilter implements IEventFilter<IEvent>
{
    
    
    private final Set<String> eventKeys;
    
    
    
    public EventTypeFilter(final String... _eventKeys) {
    
    
        eventKeys = new HashSet<String>();
        for (final String it : _eventKeys) {
            eventKeys.add(it);
        }
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.IEventFilter#isFiltered(java.io.Serializable)
     */
    @Override
    public boolean isFiltered(final IEvent _event) {
    
    
        return eventKeys.contains(_event.getEventType().getEventKey());
    }
    
}
