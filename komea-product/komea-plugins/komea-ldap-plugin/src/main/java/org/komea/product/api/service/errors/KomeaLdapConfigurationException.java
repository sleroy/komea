/**
 * 
 */

package org.komea.product.api.service.errors;


/**
 * This class defines a configuration problem in ldap plugin.
 * 
 * @author sleroy
 */
public class KomeaLdapConfigurationException extends RuntimeException {
    
    public KomeaLdapConfigurationException() {
    
        super();
        // TODO Auto-generated KomeaLdapConfigurationException stub
    }
    
    public KomeaLdapConfigurationException(final String _message, final Throwable _cause, final boolean _enableSuppression,
            final boolean _writableStackTrace) {
    
        super(_message, _cause, _enableSuppression, _writableStackTrace);
        // TODO Auto-generated KomeaLdapConfigurationException stub
    }
    
    public KomeaLdapConfigurationException(final String _message, final Throwable _cause) {
    
        super(_message, _cause);
        // TODO Auto-generated KomeaLdapConfigurationException stub
    }
    
    public KomeaLdapConfigurationException(final String _message) {
    
        super(_message);
        // TODO Auto-generated KomeaLdapConfigurationException stub
    }
    
    public KomeaLdapConfigurationException(final Throwable _cause) {
    
        super(_cause);
        // TODO Auto-generated KomeaLdapConfigurationException stub
    }
    
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
