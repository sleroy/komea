
package org.komea.product.plugins.bugzilla.core;



/**
 * 
 */


import org.junit.Test;
import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;



/**
 * @author sleroy
 */
@Service
public class BZExampleServerBean extends AbstractSpringIntegrationTestCase
{
    
    
    @Autowired
    private ApplicationContext  context;
    
    
    @Autowired
    private IBZConfigurationDAO serverDAO;
    
    
    
    @Test
    public void init() {
    
    
        // if (serverDAO.find("http://eos/bugzilla/") == null) {
        // final BZServerConfiguration bzServerConfiguration = new BZServerConfiguration();
        // bzServerConfiguration.setAddress("http://eos/bugzilla/");
        // bzServerConfiguration.setLogin("jeremie.guidoux@tocea.com");
        // bzServerConfiguration.setPassword("tocea35");
        // bzServerConfiguration.setReminderAlert(10);
        //
        // serverDAO.saveOrUpdate(bzServerConfiguration);
        // }
        // System.out.println(context);
        // final BZCheckerCron existingBean = new BZCheckerCron();
        // context.getAutowireCapableBeanFactory().autowireBean(existingBean);
        // System.out.println(existingBean);
        // existingBean.checkServers();
        
        
    }
}
