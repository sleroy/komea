
package org.komea.product.api.service.ldap;



import org.apache.directory.shared.ldap.exception.LdapConfigurationException;
import org.komea.product.backend.service.ldap.ILdapConnector;



/**
 * ILdapUserService
 * 
 * @author JavaChap
 */

public interface ILdapUserService
{
    
    
    String CRON_LDAP         = "0 0/5 * * * ?";
    
    /**
     * Ldap base path
     */
    String LDAP_BASE         = "ldap_base";
    
    /**
     * Cron Refresh key
     */
    String LDAP_CRON_REFRESH = "LDAP-CRON-REFRESH";
    
    /**
     * Password setting
     */
    String LDAP_PASSWORD     = "ldap_password";
    /**
     * Server setting
     */
    String LDAP_SERVER       = "ldap-server";
    
    /**
     * Ldap User DN
     */
    String LDAP_USER_DN      = "ldap_userDn";
    
    
    
    /**
     * Returns a ldap connector
     * 
     * @return the ldap connector.
     * @throws LdapConfigurationException
     */
    ILdapConnector newConnector() throws LdapConfigurationException;
    
    
}
