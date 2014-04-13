/**
 * 
 */

package org.komea.eventory.api.bridge;



import org.komea.product.cep.api.ICEPConfiguration;
import org.komea.product.cep.api.IEventBridge;



/**
 * This class is a factory to build a bridge that will dispatch events to queries.
 * 
 * @author sleroy
 */
public interface IEventBridgeFactory
{
    
    
    /**
     * Builds a new bridge for the cep engine.
     * 
     * @return the cep engine.
     */
    IEventBridge newBridge(ICEPConfiguration _cepConfiguration);
}
