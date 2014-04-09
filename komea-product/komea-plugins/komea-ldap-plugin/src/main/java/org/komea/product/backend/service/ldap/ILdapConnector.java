/**
 * 
 */

package org.komea.product.backend.service.ldap;



import java.io.IOException;
import java.util.List;

import org.komea.product.api.service.ldap.LdapUser;



/**
 * @author sleroy
 *
 */
public interface ILdapConnector
{
    
    
    public abstract boolean authenticate(String userName, String password);
    
    
    /*
     * (non-Javadoc)
     * @see java.io.Closeable#close()
     */
    public abstract void close() throws IOException;
    
    
    public abstract LdapUser getUser(String userName);
    
    
    public abstract List<LdapUser> getUsers(String pattern);
    
}
