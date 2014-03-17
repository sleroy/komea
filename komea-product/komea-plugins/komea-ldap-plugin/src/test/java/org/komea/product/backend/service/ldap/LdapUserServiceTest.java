/**
 * 
 */

package org.komea.product.backend.service.ldap;



import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.api.service.ldap.ILdapUserService;
import org.komea.product.api.service.ldap.LdapUser;
import org.komea.product.backend.service.ISettingService;
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
    private ISettingService  service;
    
    
    @Autowired
    private ILdapUserService userService;
    
    
    
    @Test
    public void testLdap() {
    
    
        service.getProxy(LdapUserService.LDAP_SERVER).setStringValue("ldap://localhost:33389");
        service.getProxy(LdapUserService.LDAP_PASSWORD).setStringValue("");
        service.getProxy(LdapUserService.LDAP_USER_DN).setStringValue("");
        service.getProxy(LdapUserService.LDAP_BASE).setStringValue("dc=jbcpcalendar,dc=com");
        
        final List<LdapUser> users = userService.getUsers(null);
        System.out.println(users);
        final LdapUser user = userService.getUser("user2@example.com");
        Assert.assertNotNull(user);
        
    }
    
}
