/**
 * 
 */

package org.komea.product.plugins.kpi.standard;



import org.apache.commons.lang.Validate;
import org.komea.product.cep.api.formula.IEventGroup;
import org.komea.product.cep.api.formula.IEventGroupFormula;
import org.komea.product.database.alert.IEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This formula returns the value contained in the first element of the event group.
 * 
 * @author sleroy
 */
public class EventValueFormula<T> implements IEventGroupFormula<IEvent>
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EventValueFormula.class);
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.kpi.standard.IEventGroupFormula#evalute(java.lang.Object,
     * org.komea.product.cep.api.formula.tuple.IEventGroup)
     */
    @Override
    public Number evaluate(final IEventGroup<IEvent> _eventGroup) {
    
    
        Validate.notNull(_eventGroup);
        
        if (_eventGroup.getEvents().isEmpty()) {
            LOGGER.debug("No event to return a value, may have a filter problem with events {}",
                    _eventGroup.getEvents());
            
            return 0d;
        }
        Validate.isTrue(_eventGroup.getEvents().size() == 1);
        final IEvent iEvent = _eventGroup.getFirstEvent();
        return iEvent.getValue();
        
    }
}
