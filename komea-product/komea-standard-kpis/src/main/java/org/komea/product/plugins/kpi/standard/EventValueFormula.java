/**
 * 
 */

package org.komea.product.plugins.kpi.standard;



import org.apache.commons.lang.Validate;
import org.komea.eventory.api.formula.tuple.IEventGroup;
import org.komea.product.database.alert.IEvent;
import org.komea.product.plugins.kpi.formula.IEventGroupFormula;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This formula returns the value contained in the first element of the event group.
 * 
 * @author sleroy
 */
public class EventValueFormula<T> implements IEventGroupFormula
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EventValueFormula.class);
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.kpi.standard.IEventGroupFormula#evalute(java.lang.Object,
     * org.komea.eventory.api.formula.tuple.IEventGroup)
     */
    @Override
    public Number evaluate(final IEventGroup _eventGroup) {
    
    
        Validate.notNull(_eventGroup);
        
        if (_eventGroup.getEvents().isEmpty()) {
            LOGGER.debug("No event to return a value, may have a filter problem with events {}",
                    _eventGroup.getEvents());
            
            return 0;
        }
        Validate.isTrue(_eventGroup.getEvents().size() == 1);
        final IEvent iEvent = _eventGroup.getFirstEvent();
        return iEvent.getValue();
        
    }
}
