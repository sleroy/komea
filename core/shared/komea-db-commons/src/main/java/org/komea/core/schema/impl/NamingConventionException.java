package org.komea.core.schema.impl;

import org.komea.events.exceptions.KomeaRuntimeException;

public final class NamingConventionException extends KomeaRuntimeException
{
    
    public NamingConventionException(final String message, final Throwable cause) {
    
        super(message, cause);
        
    }
    
    public NamingConventionException(final String message) {
    
        super(message);
        
    }
    
    public NamingConventionException(final Throwable t) {
    
        super(t);
        
    }
    
}