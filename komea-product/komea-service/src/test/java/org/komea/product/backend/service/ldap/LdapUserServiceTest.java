/**
 * 
 */

package org.komea.product.backend.service.ldap;



import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;



/**
 * This class defines a ldap spring test.
 * 
 * @author sleroy
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/spring/application-context-test.xml",
        "classpath*:/spring/security-spring-test.xml",
        "classpath*:/spring/ldap-spring-test.xml" })
@TransactionConfiguration(defaultRollback = true)
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
