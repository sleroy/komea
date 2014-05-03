/**
 * 
 */

package org.komea.product.cep.api.formula;



import java.io.Serializable;
import java.util.List;



/**
 * This interface describes the method provided by the event table generator. This component converts an event stream into an event table
 * grouped by a tuple creator.
 * 
 * @author sleroy
 */
public interface IEventTableGenerator
{
    
    
    /**
     * Returns the event grouped by tuple.
     * 
     * @return the events
     */
    IEventTable generateTable(List<? extends Serializable> _events);
}
