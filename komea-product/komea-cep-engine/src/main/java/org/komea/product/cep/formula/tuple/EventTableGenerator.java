/**
 * 
 */

package org.komea.product.cep.formula.tuple;



import java.io.Serializable;
import java.util.List;

import org.komea.product.cep.api.formula.tuple.IEventTable;
import org.komea.product.cep.api.formula.tuple.IEventTableGenerator;
import org.komea.product.cep.api.formula.tuple.ITupleCreator;



/**
 * This class process a stream of event and group them by tuples.
 * 
 * @author sleroy
 */
public class EventTableGenerator implements IEventTableGenerator
{
    
    
    private final ITupleCreator<Serializable> tupleCreator;
    
    
    
    /**
     * @param _tupleCreator
     */
    @SuppressWarnings("rawtypes")
    public EventTableGenerator(final ITupleCreator _tupleCreator) {
    
    
        tupleCreator = _tupleCreator;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.IEventTableGenerator#generateTupleMap(java.util.List)
     */
    @Override
    public IEventTable generateTable(final List<? extends Serializable> _events) {
    
    
        final IEventTable tupleMap = new EventTable(tupleCreator);
        for (final Serializable event : _events) {
            tupleMap.groupEvent(event);
        }
        return tupleMap;
    }
}
