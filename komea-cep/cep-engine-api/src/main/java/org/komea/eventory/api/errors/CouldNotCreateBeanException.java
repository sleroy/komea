/**
 * 
 */

package org.komea.eventory.api.errors;



/**
 * @author sleroy
 */
public class CouldNotCreateBeanException extends EventoryException
{
    
    
    public CouldNotCreateBeanException(final Class<?> _class, final Throwable _throwable) {
    
    
        super("Could not instantiate the class " + _class.getName(), _throwable);
    }
    
    
}
