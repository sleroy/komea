/**
 * 
 */

package org.komea.product.backend.service.ldap;



import java.io.IOException;
import java.util.List;

import org.apache.directory.shared.ldap.exception.LdapConfigurationException;
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
@ContextConfiguration(
        locations =
            { "classpath*:/spring/application-context-test.xml", "classpath*:/spring/security-spring-test.xml", "classpath*:/spring/ldap-spring-test.xml" })
@TransactionConfiguration(defaultRollback = true)
public class LdapUserServiceTest extends AbstractSpringIntegrationTestCase
{
    
    
    @Autowired
    private ISettingService  service;
    
    
    @Autowired
    private ILdapUserService userService;
    
    
    
    @Test
    public void testLdap() {
    
    
        service.getProxy(ILdapUserService.LDAP_SERVER).setStringValue("ldap://localhost:33389");
        service.getProxy(ILdapUserService.LDAP_PASSWORD).setStringValue("");
        service.getProxy(ILdapUserService.LDAP_USER_DN).setStringValue("");
        service.getProxy(ILdapUserService.LDAP_BASE).setStringValue("dc=jbcpcalendar,dc=com");
        
        try {
            final LdapConnector ldapConnector = new LdapConnector();
            ldapConnector.setSettingService(service);
            ldapConnector.initConnector();
            
            final List<LdapUser> users = ldapConnector.getUsers(null);
            System.out.println(users);
            final LdapUser user = ldapConnector.getUser("user2@example.com");
            Assert.assertNotNull(user);
            
            ldapConnector.close();
        } catch (final LdapConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
}
