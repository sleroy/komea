/**
 * 
 */

package org.komea.product.plugins.bugzilla.service;



import org.komea.product.backend.exceptions.KomeaRuntimeException;



/**
 * @author sleroy
 */
public class BugzillaPluginException extends KomeaRuntimeException
{
    
    
    /**
     * @param _throwable
     */
    public BugzillaPluginException(final Throwable _throwable) {
    
    
        super(_throwable);
    }
    
    
}
