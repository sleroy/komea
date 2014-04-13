/**
 * 
 */

package org.komea.eventory.api.engine;



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
    String getBridgeImplementation();
    
    
    /**
     * Returns the cache implementation
     * 
     * @return the cache implementation;
     */
    String getCacheImplementation();
    
    
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
}
