/**
 * 
 */

package org.komea.product.backend.service.generic;



import org.komea.product.database.api.IHasKey;



/**
 * This interface defines the registry that stores event listeners and notifies them of dao modifications
 * 
 * @author sleroy
 * @param <PK>
 */
public interface IDAOEventRegistry
{
    
    
    /**
     * Entity prepared to be deleted.
     * 
     * @param _key
     */
    public void notifyDeleted(IHasKey _key);
    
    
    /**
     * Entity prepared to be deleted.
     * 
     * @param _key
     */
    public void notifyUpdated(IHasKey _key);
    
}
