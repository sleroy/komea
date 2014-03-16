/**
 * 
 */

package org.komea.product.cep.api.formula.tuple;



import java.io.Serializable;
import java.util.Map.Entry;



/**
 * This interface defines the storage of events grouped by tuple. Usually the implementation takes a {@link ITupleCreator} to produce tuple
 * from an event.
 * 
 * @author sleroy
 */
public interface IEventTable
{
    
    
    /**
     * Group an event by tuple inside the map.
     * 
     * @param _event
     *            the event to group;
     */
    void groupEvent(Serializable _event);
    
    
    /**
     * Returns an iterator on the tuple map.
     * 
     * @return an iterator.
     */
    Iterable<Entry<ITuple, IEventGroup>> iterator();
    //
}
