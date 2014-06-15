/**
 *
 */

package org.komea.eventory.bridge;



import org.komea.eventory.api.bridge.IEventBridge;
import org.komea.eventory.api.bridge.IEventBridgeFactory;
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
    public IEventBridge newBridge() {


        return new MemoryBridge();
    }

}
