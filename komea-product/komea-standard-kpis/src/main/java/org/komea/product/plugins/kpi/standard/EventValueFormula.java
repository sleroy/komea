/**
 * 
 */

package org.komea.product.plugins.kpi.standard;



import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;
import org.komea.product.cep.api.ITupleResultMap;
import org.komea.product.cep.api.formula.tuple.IEventGroup;
import org.komea.product.cep.api.formula.tuple.IEventTable;
import org.komea.product.cep.api.formula.tuple.ITuple;
import org.komea.product.cep.api.formula.tuple.ITuplerFormula;
import org.komea.product.cep.formula.tuple.TupleResultMap;
import org.komea.product.database.alert.IEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This formula returns the value contained in the first element of the event group.
 * 
 * @author sleroy
 */
public class EventValueFormula implements ITuplerFormula<Number>
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EventValueFormula.class);
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.ITuplerFormula#processMap(org.komea.product.cep.api.formula.tuple.IEventTable,
     * java.util.Map)
     */
    @Override
    public ITupleResultMap processMap(final IEventTable _tupleMap, final Map _ownParameters) {
    
    
        Validate.notNull(_tupleMap);
        Validate.notNull(_ownParameters);
        final ITupleResultMap<Number> resultMap = new TupleResultMap<Number>();
        
        for (final Entry<ITuple, IEventGroup> entry : _tupleMap.iterator()) {
            if (entry.getValue().getEvents().isEmpty()) {
                LOGGER.error(
                        "No event to return a value, may have a filter problem with tuple {} events {}",
                        entry, entry.getValue().getEvents());
                
                continue;
            }
            Validate.isTrue(entry.getValue().getEvents().size() == 1);
            final IEvent iEvent = entry.getValue().getFirstEvent();
            resultMap.insertEntry(entry.getKey(), iEvent.getValue());
        }
        return resultMap;
    }
}
