/**
 * 
 */

package org.komea.eventory.api.engine;



import java.io.Serializable;



/**
 * This interface defines an event listener on CEP Engine.
 * 
 * @author sleroy
 */
public interface ICEPEventListener
{
    
    
    /**
     * Notify that an event has been received in esper.
     * 
     * @param _event
     *            the event.
     */
    public void notify(Serializable _event);
    
    
}
