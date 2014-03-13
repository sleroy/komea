/**
 * 
 */

package org.komea.product.cep.api.formula.tuple;



import java.io.Serializable;
import java.util.List;



/**
 * This class defines the event tupler.
 * 
 * @author sleroy
 */
public interface IEventTupler
{
    
    
    /**
     * Returns the event grouped by tuple.
     * 
     * @return the events
     */
    ITupleMap generateTupleMap(List<? extends Serializable> _events);
}
