/**
 * 
 */

package org.komea.product.backend.service;



import java.util.List;



/**
 * @author sleroy
 */
public interface IAdminActionServices
{
    
    
    /**
     * Admins the action.
     * 
     * @param _adminAction
     *            the admin action.
     * @return the result of the execution.
     */
    public String executeAction(IAdminAction _adminAction);
    
    
    /**
     * Return the list of actions
     * 
     * @return the list of actions
     */
    public List<IAdminAction> getActions();
    
    
    /**
     * Registers an admin action.
     * 
     * @param _adminAction
     *            the admin action.
     */
    public void registerAdminAction(IAdminAction _adminAction);
    
}
