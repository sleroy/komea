/**
 *
 */

package org.komea.product.api.service.ldap;


import java.io.IOException;
import java.util.List;
import org.komea.product.backend.service.ldap.LdapServer;

/**
 * This class defines the ldap connector.
 *
 * @author sleroy
 */
public interface ILdapConnector {
    
    public boolean authenticate(String userName, String password);
    
    /*
     * (non-Javadoc)
     * @see java.io.Closeable#close()
     */
    public void close() throws IOException;
    
    /**
     * This method get LDAP user inforamtion from this lofin
     * 
     * @param userName
     *            the ldap user login
     * @return
     */
    public LdapUser getUser(String userName);
    
    /**
     * This method get the LDAP users list informations from a research pattern
     * 
     * @param pattern
     *            the research pattern based on users login
     * @return the user list inforamtion
     */
    public List<LdapUser> getUsers(String pattern);
    
    /**
     * This method check if the ldap connection has been initialized
     * 
     * @return
     */
    boolean isInit();
    
    /**
     * This method init connection to the LDAP server using the ldap configuration
     */
    void initConnection();
    
    boolean testConnexion(LdapServer _ldapServer);
    
}
