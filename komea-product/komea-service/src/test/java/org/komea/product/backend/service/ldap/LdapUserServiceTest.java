/**
 * 
 */

package org.komea.product.backend.service.ldap;



import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.backend.service.ldap.LdapUser;
import org.komea.product.backend.service.ldap.ILdapUserService;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * This class defines a ldap spring test.
 * 
 * @author sleroy
 */
public class LdapUserServiceTest extends AbstractSpringIntegrationTestCase
{
    
    
    @Autowired
    private ILdapUserService userService;
    
    
    
    @Test
    public void testLdap() {
    
    
        final List<LdapUser> users = userService.getUsers(null);
        System.out.println(users);
        final LdapUser user = userService.getUser("user2@example.com");
        Assert.assertNotNull(user);
        
    }
    
}
