/**
 * 
 */

package org.komea.product.cep.api.formula;



import java.io.Serializable;



/**
 * @author sleroy
 */
public interface IEventGroupFormula<T extends Serializable>
{
    
    
    /**
     * Evaluates a formula on the event group of an entity
     * 
     * @param _eventGroup
     * @return the number
     */
    Number evaluate(IEventGroup<T> _eventGroup);
}
