/**
 * 
 */

package org.komea.product.plugins.repository.model;



import java.io.Serializable;



/**
 * @author sleroy
 */
public class ScmExecutionStatus implements Serializable
{
    
    
    private Throwable lastError;
    
    
    
    public ScmExecutionStatus() {
    
    
        super();
    }
    
    
    public ScmExecutionStatus(final Throwable _throwable) {
    
    
        super();
        lastError = _throwable;
    }
    
    
    /**
     * Returns the last error
     * 
     * @return the last error.
     */
    public Throwable getLastError() {
    
    
        return lastError;
    }
    
    
    /**
     * Tests if the last check-in of the scm repository definition was a success
     * 
     * @return true if the scm repository.
     */
    public boolean isSuccess() {
    
    
        return lastError == null;
    }
    
    
    public void setLastError(final Throwable _lastError) {
    
    
        lastError = _lastError;
    }
    
}
