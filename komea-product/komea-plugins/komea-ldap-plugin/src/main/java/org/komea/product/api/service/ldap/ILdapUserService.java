
package org.komea.product.api.service.ldap;




import org.komea.product.api.service.errors.KomeaLdapConfigurationException;



/**
 * ILdapUserService
 * 
 * @author JavaChap
 */

public interface ILdapUserService
{
    
    
    String CRON_LDAP         = "0 0/10   * * * ?";
    
    String LDAP_AUTH_TYPE    = "ldap_authType";
    
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
    ILdapConnector newConnector() throws KomeaLdapConfigurationException;
    
    
}
