/**
 * 
 */

package org.komea.product.cep.api.formula;



import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.komea.product.cep.api.formula.tuple.ITupleCreator;



/**
 * This interface defines the storage of events grouped by tuple. Usually the implementation takes a {@link ITupleCreator} to produce tuple
 * from an event.
 * 
 * @author sleroy
 */
public interface IEventTable<K, TE extends Serializable>
{
    
    
    /**
     * Fill the event table with a list of events.
     * 
     * @param _aggregateView
     */
    void fill(List<TE> _aggregateView);
    
    
    /**
     * Returns the event group with the given key
     * 
     * @param _of
     *            the event group
     * @return
     */
    IEventGroup<TE> get(K _of);
    
    
    /**
     * Returns a map representing the event table.
     * 
     * @return a map indexed by K
     */
    Map<K, IEventGroup<TE>> getMap();
    
    
    /**
     * Group an event by tuple inside the map.
     * 
     * @param _event
     *            the event to group;
     */
    void groupEvent(TE _event);
    
    
    /**
     * @return the list of entries of the table.
     */
    Set<Entry<K, IEventGroup<TE>>> iterator();
    
    
    /**
     * Returns the size of the table
     */
    int size();
}
