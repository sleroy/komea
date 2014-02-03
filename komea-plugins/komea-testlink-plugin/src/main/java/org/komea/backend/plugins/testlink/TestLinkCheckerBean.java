/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.testlink;

import java.util.List;
import org.komea.backend.plugins.testlink.api.ITestLinkAlertFactory;
import org.komea.backend.plugins.testlink.api.ITestLinkServerConfiguration;
import org.komea.backend.plugins.testlink.api.ITestLinkServerManagerService;
import org.komea.backend.plugins.testlink.api.ITestLinkServerProxy;
import org.komea.product.backend.service.esper.IAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *
 * @author rgalerme
 */
public class TestLinkCheckerBean {

    @Autowired
    private IAlertService alertService;

    @Autowired
    private ITestLinkAlertFactory alertFactory;

    @Autowired
    private ITestLinkServerManagerService serverConfig;
    
    @Scheduled(fixedDelay = 10)
    public void checkServers() {
        List<ITestLinkServerConfiguration> servers = this.serverConfig.getServers();
        for (ITestLinkServerConfiguration testlinkServer : servers) {
            ITestLinkServerProxy openProxy = testlinkServer.openProxy();
            // traitement spécifique au plugin
            
            
            
        }
    }
    
}

