/**
 * 
 */

package org.komea.eventory.api.errors;



/**
 * @author sleroy
 */
public class PluginImplementationNotFoundException extends EventoryException
{
    
    
    public PluginImplementationNotFoundException(final String _class, final Throwable _throwable) {
    
    
        super("Could not find the implementation " + _class, _throwable);
    }
    
    
}
