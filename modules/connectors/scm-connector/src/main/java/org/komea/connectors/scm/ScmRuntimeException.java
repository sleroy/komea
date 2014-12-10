
package org.komea.connectors.scm;


import org.komea.core.exceptions.KomeaRuntimeException;

public class ScmRuntimeException extends KomeaRuntimeException
{
    
    public ScmRuntimeException() {
    
        super();
        
    }
    
    public ScmRuntimeException(final String message, final Throwable cause) {
    
        super(message, cause);
        
    }
    
    public ScmRuntimeException(final String message) {
    
        super(message);
        
    }
    
    public ScmRuntimeException(final Throwable t) {
    
        super(t);
        
    }
    
}
