/**
 * 
 */

package org.komea.eventory.api.engine;



import java.io.File;

import org.komea.eventory.api.bridge.IEventBridgeFactory;
import org.komea.eventory.api.cache.ICacheStorageFactory;



/**
 * This interface defines the configuration of the CEP Engine.
 * 
 * @author sleroy
 */
public interface ICEPConfiguration
{
    
    
    /**
     * Returns the plugin implementation.
     * 
     * @return the plugin implementation.
     */
    IEventBridgeFactory getBridgeFactory();
    
    
    /**
     * Returns the factory to build cache.
     * 
     * @return the factory to build the caches.
     */
    ICacheStorageFactory getCacheStorageFactory();
    
    
    /**
     * Returns the running mode of the cep engine.
     * 
     * @return the running mode.
     */
    RunningMode getMode();
    
    
    /**
     * Returns the number of query listeners
     * 
     * @return the number of query listeners.
     */
    int getNumberQueryListeners();
    
    
    /**
     * @return
     */
    File getStorageFolder();
}
