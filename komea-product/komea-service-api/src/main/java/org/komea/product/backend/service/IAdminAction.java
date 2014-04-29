/**
 * 
 */

package org.komea.product.backend.service;



/**
 * This class executes the admin action.
 * 
 * @author sleroy
 */
public interface IAdminAction
{
    
    
    /**
     * Executes the admin action.
     */
    void execute(ProgressListener _logger);
    
    
    /**
     * Returns the action name
     * 
     * @return the action name
     */
    String getActionName();
    
}
