
package org.komea.product.api.service.ldap;



import java.util.List;



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
     * Method to test authentication for an ldap user.
     * 
     * @param userName
     * @param password
     * @return
     */
    boolean authenticate(final String userName, final String password);
    
    
    LdapUser getUser(final String email);
    
    
    List<LdapUser> getUsers(final String pattern);
    
    
    /**
     * Refresh the plugin since properties have been modified.
     */
    void refreshPlugin();
    
}
