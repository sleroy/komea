/**
 * 
 */

package org.komea.product.cep.api;



import java.io.Serializable;



/**
 * This interface defines the CEP Engine.
 * 
 * @author sleroy
 */
public interface ICEPEngine
{
    
    
    /**
     * This method pushes an event inside the CEP ENgine.
     * 
     * @param _event
     *            the event.
     */
    void pushEvent(Serializable _event);
    
    
    /**
     * Register a event listener
     * 
     * @param _name
     *            the event name
     * @param _listener
     *            the vent listener.
     */
    void registerEventListener(String _name, ICEPEventListener _listener);
    
    
    /**
     * Remove the event listener
     * 
     * @param _name
     *            the listener name.
     */
    void removeEventListener(String _name);
}
