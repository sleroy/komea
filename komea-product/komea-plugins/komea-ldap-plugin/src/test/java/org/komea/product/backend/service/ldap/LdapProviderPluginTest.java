/**
 * 
 */

package org.komea.product.backend.service.ldap;


import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.api.service.errors.KomeaLdapConfigurationException;
import org.komea.product.api.service.ldap.ILdapConnector;
import org.komea.product.api.service.ldap.ILdapService;
import org.komea.product.api.service.ldap.ILdapProviderPlugin;
import org.komea.product.api.service.ldap.LdapUser;
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
        "classpath*:/spring/application-context-test.xml", "classpath*:/spring/security-spring-test.xml",
        "classpath*:/spring/ldap-spring-test.xml" })
@TransactionConfiguration(defaultRollback = true)
public class LdapProviderPluginTest extends AbstractSpringIntegrationTestCase {
    
    @Autowired
    private ILdapService     service;
    
    @Autowired
    private ILdapProviderPlugin userService;
    
    @Autowired
    private ILdapConnector   ldapConnector;
    
    @Test
    public void testLdap() {
    
        LdapServer ldapServer = new LdapServer();
        ldapServer.setLdapUrl("ldap://localhost:33389");
        ldapServer.setLdapPassword("");
        ldapServer.setLdapUserDN("");
        ldapServer.setLdapBase("dc=jbcpcalendar,dc=com");
        service.saveOrUpdate(ldapServer);
        
        try {
            ldapConnector.initConnection();
            
            final List<LdapUser> users = ldapConnector.getUsers(null);
            System.out.println(users);
            final LdapUser user = ldapConnector.getUser("user2@example.com");
            Assert.assertNotNull(user);
            
            ldapConnector.close();
        } catch (final KomeaLdapConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Assert.fail(e.getMessage());
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
        
    }
    
}
