/**
 * 
 */

package org.komea.product.cep.api.formula.tuple;



import java.io.Serializable;



/**
 * This interface defines the tuple creator
 * 
 * @author sleroy
 */
public interface ITupleCreator<TEvent extends Serializable, TRes>
{
    
    
    /**
     * Creates a tuple
     * 
     * @param _event
     *            the event
     * @return the new tuple
     */
    TRes create(TEvent _event);
}
