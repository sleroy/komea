/**
 * 
 */

package org.komea.eventory.bridge;



import org.komea.eventory.api.bridge.IEventBridgeFactory;
import org.komea.product.cep.api.ICEPConfiguration;
import org.komea.product.cep.api.IEventBridge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author sleroy
 */
public class BridgeFactory implements IEventBridgeFactory
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BridgeFactory.class);
    
    
    
    /**
     * 
     */
    public BridgeFactory() {
    
    
        super();
        LOGGER.info("Initialisation of a event memory bridge");
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.bridge.IEventBridgeFactory#newBridge(org.komea.product.cep.api.ICEPConfiguration)
     */
    @Override
    public IEventBridge newBridge(final ICEPConfiguration _cepConfiguration) {
    
    
        return new MemoryBridge(_cepConfiguration);
    }
    
}
