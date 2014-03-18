/**
 * 
 */

package org.komea.product.backend.genericservice;



import org.komea.product.backend.service.generic.IDAOEventRegistry;
import org.komea.product.database.api.IHasKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



/**
 * This component collects all the events of entity modifications.
 * 
 * @author sleroy
 */
@Service
public class DAOEventRegistry implements IDAOEventRegistry
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DAOEventRegistry.class);
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.generic.IDAOEventRegistry#notifyDeleted(org.komea.product.database.api.IHasKey)
     */
    @Override
    public void notifyDeleted(final IHasKey _key) {
    
    
        if (_key != null) {
            LOGGER.trace("{} has been deleted", _key);
        }
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.generic.IDAOEventRegistry#notifyUpdated(org.komea.product.database.api.IHasKey)
     */
    @Override
    public void notifyUpdated(final IHasKey _key) {
    
    
        if (_key != null) {
            LOGGER.trace("{} has been updated", _key);
        }
        
        
    }
}
