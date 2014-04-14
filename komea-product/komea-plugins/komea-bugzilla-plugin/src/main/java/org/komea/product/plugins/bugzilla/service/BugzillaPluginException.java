/**
 * 
 */

package org.komea.product.plugins.bugzilla.service;



import org.komea.product.backend.exceptions.KomeaException;



/**
 * @author sleroy
 */
public class BugzillaPluginException extends KomeaException
{
    
    
    /**
     * @param _throwable
     */
    public BugzillaPluginException(final Throwable _throwable) {
    
    
        super(_throwable);
    }
    
    
}
