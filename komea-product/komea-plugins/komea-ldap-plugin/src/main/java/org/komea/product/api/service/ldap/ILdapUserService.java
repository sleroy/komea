
package org.komea.product.api.service.ldap;



import java.util.List;



/**
 * ILdapUserService
 * 
 * @author JavaChap
 */

public interface ILdapUserService
{
    
    
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
