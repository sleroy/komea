/**
 * 
 */

package org.komea.product.backend.service;



import javax.annotation.PostConstruct;

import org.komea.product.plugins.testlink.api.ITestLinkServerDAO;
import org.komea.product.plugins.testlink.model.TestLinkServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * @author sleroy
 */
@Service
public class TestlinkExampleServerBean
{
    
    
    @Autowired
    private ITestLinkServerDAO serverDAO;
    
    
    
    @PostConstruct
    public void init() {
    
    
        if (serverDAO.find("ARES") == null) {
            serverDAO.saveOrUpdate(new TestLinkServer(
                    "http://ares.tocea/testlink/lib/api/xmlrpc.php",
                    "2dec70df08045278463817fb15d79c4d", "ARES"));
        }
        
    }
}
