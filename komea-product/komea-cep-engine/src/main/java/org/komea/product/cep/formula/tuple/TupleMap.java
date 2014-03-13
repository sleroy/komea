/**
 * 
 */

package org.komea.product.cep.formula.tuple;



import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;
import org.komea.product.cep.api.formula.tuple.IEventGroup;
import org.komea.product.cep.api.formula.tuple.ITuple;
import org.komea.product.cep.api.formula.tuple.ITupleCreator;
import org.komea.product.cep.api.formula.tuple.ITupleMap;



/**
 * This class is the storage for events grouped by tuples. It employs a tuple creator to produce key for the map.
 * 
 * @author sleroy
 */
public class TupleMap implements ITupleMap
{
    
    
    private final ITupleCreator<Serializable> tupleCreator;
    
    private final Map<ITuple, IEventGroup>    tupleMap = new HashMap<ITuple, IEventGroup>();
    
    
    
    /**
     * @param _tupleCreator
     */
    public TupleMap(@SuppressWarnings("rawtypes")
    final ITupleCreator _tupleCreator) {
    
    
        super();
        tupleCreator = _tupleCreator;
        
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.ITupleMap#groupEvent(java.io.Serializable)
     */
    @Override
    public void groupEvent(final Serializable _event) {
    
    
        Validate.notNull(_event);
        final ITuple tuple = tupleCreator.create(_event);
        Validate.notNull(_event);
        IEventGroup iEventGroup = tupleMap.get(tuple);
        if (iEventGroup == null) {
            tupleMap.put(tuple, iEventGroup = new EventGroup());
        }
        iEventGroup.addEvent(_event);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.ITupleMap#iterator()
     */
    @Override
    public Iterable<Entry<ITuple, IEventGroup>> iterator() {
    
    
        return Collections.unmodifiableSet(tupleMap.entrySet());
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "TupleMap [tupleCreator=" + tupleCreator + ", tupleMap=" + tupleMap + "]";
    }
    
}
