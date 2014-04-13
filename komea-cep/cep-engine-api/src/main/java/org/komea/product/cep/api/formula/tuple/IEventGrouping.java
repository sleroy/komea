/**
 * 
 */

package org.komea.product.cep.api.formula.tuple;




/**
 * @author sleroy
 */
public interface IEventGrouping
{
    
    
    /**
     * Tests if the tuple is existing
     * 
     * @param _tuple
     *            the tuple
     */
    boolean existTuple(ITuple _tuple);
    
    
    /**
     * Returns the group associated to the tuple. If the tuple is not registered, an event group will be automatically created.
     * 
     * @param _tuple
     *            the tuple
     * @return the event group
     */
    IEventGroup getGroup(ITuple _tuple);
}
