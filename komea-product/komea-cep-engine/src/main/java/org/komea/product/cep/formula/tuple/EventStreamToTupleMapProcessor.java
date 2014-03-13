/**
 * 
 */

package org.komea.product.cep.formula.tuple;



import java.io.Serializable;
import java.util.List;

import org.komea.product.cep.api.formula.tuple.IEventTupler;
import org.komea.product.cep.api.formula.tuple.ITupleCreator;
import org.komea.product.cep.api.formula.tuple.ITupleMap;



/**
 * This class process a stream of event and group them by tuples.
 * 
 * @author sleroy
 */
public class EventStreamToTupleMapProcessor implements IEventTupler
{
    
    
    private final ITupleCreator tupleCreator;
    
    
    
    /**
     * @param _tupleCreator
     */
    @SuppressWarnings("rawtypes")
    public EventStreamToTupleMapProcessor(final ITupleCreator _tupleCreator) {
    
    
        tupleCreator = _tupleCreator;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.IEventTupler#generateTupleMap(java.util.List)
     */
    @Override
    public ITupleMap generateTupleMap(final List<? extends Serializable> _events) {
    
    
        final ITupleMap tupleMap = new TupleMap(tupleCreator);
        for (final Serializable event : _events) {
            tupleMap.groupEvent(event);
        }
        return tupleMap;
    }
}
