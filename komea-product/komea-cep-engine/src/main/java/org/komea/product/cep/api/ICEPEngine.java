/**
 * 
 */

package org.komea.product.cep.api;



import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;



/**
 * This interface defines the CEP Engine.
 * 
 * @author sleroy
 */
public interface ICEPEngine extends Closeable
{
    
    
    /**
     * Returns the engine configuration.
     * 
     * @return the engine configuration.
     */
    ICEPConfiguration getConfiguration();
    
    
    /**
     * Returns the query administration
     * 
     * @return the query administrator
     */
    IQueryAdministrator getQueryAdministration();
    
    
    /**
     * Initializes / restarts the CEP Engine.
     * 
     * @throws IOException
     */
    void initialize() throws IOException;
    
    
    /**
     * This method pushes an event inside the CEP ENgine.
     * 
     * @param _event
     *            the event.
     */
    void pushEvent(Serializable _event);
    
    
}
