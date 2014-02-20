/**
 * 
 */

package org.komea.product.backend.genericservice;



import org.komea.product.backend.service.generic.IDAOEventRegistry;
import org.komea.product.database.api.IHasKey;
import org.springframework.stereotype.Service;



/**
 * This component collects all the events from entities modification.
 * 
 * @author sleroy
 */
@Service
public class DAOEventRegistry implements IDAOEventRegistry
{
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.generic.IDAOEventRegistry#notifyDeleted(org.komea.product.database.api.IHasKey)
     */
    @Override
    public void notifyDeleted(final IHasKey _key) {
    
    
        // TODO Auto-generated method stub
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.generic.IDAOEventRegistry#notifyUpdated(org.komea.product.database.api.IHasKey)
     */
    @Override
    public void notifyUpdated(final IHasKey _key) {
    
    
        // TODO Auto-generated method stub
        
    }
}
