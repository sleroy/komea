/**
 * 
 */

package org.komea.product.api.service.errors;



/**
 * This class defines a configuration problem in ldap plugin.
 * 
 * @author sleroy
 */
public class KomeaLdapConfigurationException extends RuntimeException
{
    
    
    /**
     * 
     */
    private static final long serialVersionUID = 4642607235829553525L;
    
    
    
    /**
     * @param _message
     * @param _e
     */
    public KomeaLdapConfigurationException(final String _message, final Exception _e) {
    
    
        super("Problem with LDAP Configuration : " + _message, _e);
    }
    
}
