/**
 * 
 */

package org.komea.product.cep.api;



/**
 * This interface defines the configuration of the CEP Engine.
 * 
 * @author sleroy
 */
public interface ICEPConfiguration
{
    
    
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
