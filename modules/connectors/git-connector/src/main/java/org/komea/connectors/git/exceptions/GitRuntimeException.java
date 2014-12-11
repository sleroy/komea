
package org.komea.connectors.git.exceptions;


import org.komea.core.exceptions.KomeaRuntimeException;

public class GitRuntimeException extends KomeaRuntimeException
{
    
    public GitRuntimeException(final Throwable _throwable) {
    
        super(_throwable);
    }
    
    public GitRuntimeException(final String message, final Throwable cause) {
    
        super(message, cause);
        
    }
    
    public GitRuntimeException(final String message) {
    
        super(message);
        
    }
    
}
