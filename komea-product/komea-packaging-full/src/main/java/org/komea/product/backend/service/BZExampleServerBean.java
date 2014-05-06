/**
 * 
 */

package org.komea.product.backend.service;



import javax.annotation.PostConstruct;

import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * @author sleroy
 */
@Service
public class BZExampleServerBean
{
    
    
    @Autowired
    private IBZConfigurationDAO serverDAO;
    
    
    
    @PostConstruct
    public void init() {
    
    
        if (serverDAO.find("http://eos/bugzilla/") == null) {
            final BZServerConfiguration bzServerConfiguration = new BZServerConfiguration();
            bzServerConfiguration.setAddress("http://eos/bugzilla/");
            bzServerConfiguration.setLogin("jeremie.guidoux@tocea.com");
            bzServerConfiguration.setPassword("tocea35");
            bzServerConfiguration.setReminderAlert(10);
            
            serverDAO.saveOrUpdate(bzServerConfiguration);
        }
        
    }
}
