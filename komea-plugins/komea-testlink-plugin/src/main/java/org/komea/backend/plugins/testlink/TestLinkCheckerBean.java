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
import org.komea.product.backend.service.esper.IAlertPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;



/**
 * @author rgalerme
 */
public class TestLinkCheckerBean
{
    
    
    @Autowired
    private IAlertPushService             alertService;
    
    
    @Autowired
    private ITestLinkAlertFactory         alertFactory;
    
    
    @Autowired
    private ITestLinkServerManagerService serverConfig;
    
    
    
    @Scheduled(fixedDelay = 10)
    public void checkServers() {
    
    
        final List<ITestLinkServerConfiguration> servers = serverConfig.getServers();
        for (final ITestLinkServerConfiguration testlinkServer : servers) {
            final ITestLinkServerProxy openProxy = testlinkServer.openProxy();
            // traitement spécifique au plugin
            
            
        }
    }
    
    
    /**
     * @return the alertFactory
     */
    public ITestLinkAlertFactory getAlertFactory() {
    
    
        return alertFactory;
    }
    
    
    /**
     * @return the alertService
     */
    public IAlertPushService getAlertService() {
    
    
        return alertService;
    }
    
    
    /**
     * @return the serverConfig
     */
    public ITestLinkServerManagerService getServerConfig() {
    
    
        return serverConfig;
    }
    
    
    /**
     * @param _alertFactory
     *            the alertFactory to set
     */
    public void setAlertFactory(final ITestLinkAlertFactory _alertFactory) {
    
    
        alertFactory = _alertFactory;
    }
    
    
    /**
     * @param _alertService
     *            the alertService to set
     */
    public void setAlertService(final IAlertPushService _alertService) {
    
    
        alertService = _alertService;
    }
    
    
    /**
     * @param _serverConfig
     *            the serverConfig to set
     */
    public void setServerConfig(final ITestLinkServerManagerService _serverConfig) {
    
    
        serverConfig = _serverConfig;
    }
    
}
