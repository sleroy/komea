/**
 * 
 */

package org.komea.product.api.service.ldap;



import java.io.IOException;
import java.util.List;



/**
 * This class defines the ldap connector.
 * 
 * @author sleroy
 */
public interface ILdapConnector
{
    
    
    public boolean authenticate(String userName, String password);
    
    
    /*
     * (non-Javadoc)
     * @see java.io.Closeable#close()
     */
    public void close() throws IOException;
    
    
    public LdapUser getUser(String userName);
    
    
    public List<LdapUser> getUsers(String pattern);
    
}
