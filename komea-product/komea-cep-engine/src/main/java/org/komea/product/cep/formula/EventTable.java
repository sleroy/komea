/**
 * 
 */

package org.komea.product.cep.formula;



import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.komea.product.cep.api.formula.IEventGroup;
import org.komea.product.cep.api.formula.IEventTable;
import org.komea.product.cep.api.formula.tuple.ITupleCreator;

import com.google.common.collect.Maps;



/**
 * This class is the storage for events grouped by tuples. It employs a tuple creator to produce key for the map.
 * 
 * @param <K>
 *            the key used to group events
 * @param <TE>
 *            the type of event allowed
 * @author sleroy
 */
public class EventTable<K, TE extends Serializable> implements IEventTable<K, TE>
{
    
    
    private final ITupleCreator<TE, K>    tupleCreator;
    
    private final Map<K, IEventGroup<TE>> tupleEventTable = Maps.newHashMap();
    
    
    
    /**
     * @param _tupleCreator
     */
    public EventTable(@SuppressWarnings("rawtypes")
    final ITupleCreator<TE, K> _tupleCreator) {
    
    
        super();
        tupleCreator = _tupleCreator;
        
        
    }
    
    
    /**
     * Fill the event table of events.
     * 
     * @param _aggregateView
     */
    @Override
    public void fill(final List<TE> _aggregateView) {
    
    
        for (final TE event : _aggregateView) {
            groupEvent(event);
        }
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.IEventTable#get(org.komea.product.service.dto.EntityKey)
     */
    @Override
    public IEventGroup<TE> get(final K _key) {
    
    
        return tupleEventTable.get(_key);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.IEventTable#getMap()
     */
    @Override
    public Map<K, IEventGroup<TE>> getMap() {
    
    
        return tupleEventTable;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.IEventTable#groupEvent(java.io.Serializable)
     */
    @Override
    public void groupEvent(final TE _event) {
    
    
        Validate.notNull(_event);
        final K tuple = tupleCreator.create(_event);
        Validate.notNull(_event);
        IEventGroup<TE> iEventGroup = tupleEventTable.get(tuple);
        if (iEventGroup == null) {
            tupleEventTable.put(tuple, iEventGroup = new EventGroup<TE>());
        }
        iEventGroup.addEvent(_event);
        
    }
    
    
    /**
     * Returns the lis tof entries in the table;
     * 
     * @return
     */
    @Override
    public Set<Entry<K, IEventGroup<TE>>> iterator() {
    
    
        return tupleEventTable.entrySet();
    }
    
    
    /**
     * Returns the size
     * 
     * @return
     */
    @Override
    public int size() {
    
    
        return tupleEventTable.size();
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.K#toString()
     */
    @Override
    public String toString() {
    
    
        return "EventTable [tupleCreator="
                + tupleCreator + ", tupleEventTable=" + tupleEventTable + "]";
    }
    
}
