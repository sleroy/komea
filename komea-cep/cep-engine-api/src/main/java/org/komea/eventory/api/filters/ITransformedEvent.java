/**
 * 
 */

package org.komea.eventory.api.filters;



/**
 * @author sleroy
 * @param S
 *            the type of the transformed event.
 */
public interface ITransformedEvent<S>
{
    
    
    /**
     * Returns the data
     * 
     * @return the data.
     */
    S getData();
    
    
    /**
     * Returns true if the data is valid and should be inserted.
     * 
     * @return true if the data is valid.
     */
    boolean isValid();
}
