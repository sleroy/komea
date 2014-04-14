/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.testlink.core;



import java.util.List;

import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.plugins.testlink.api.ITestLinkAlertFactory;
import org.komea.product.plugins.testlink.api.ITestLinkAnalysisService;
import org.komea.product.plugins.testlink.api.ITestLinkServerDAO;
import org.komea.product.plugins.testlink.api.ITestLinkServerProxy;
import org.komea.product.plugins.testlink.api.ITestLinkServerProxyFactory;
import org.komea.product.plugins.testlink.model.TestLinkProject;
import org.komea.product.plugins.testlink.model.TestLinkServer;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * This class is the cron job that enables the task to check periodically testlink servers.
 * 
 * @author rgalerme
 */

public class TestLinkCheckerCron implements Job
{
    
    
    private static final Logger         LOGGER = LoggerFactory.getLogger("testlink-cron");
    
    @Autowired
    private ITestLinkAlertFactory       alertFactory;
    
    @Autowired
    private IEventPushService           alertService;
    
    @Autowired
    private ITestLinkAnalysisService    analysisService;
    
    
    @Autowired
    private ITestLinkServerProxyFactory proxyFactory;
    
    
    @Autowired
    private ITestLinkServerDAO          serverConfig;
    
    
    
    /**
     * Check all the testlink servers
     */
    public void checkServers() {
    
    
        final List<TestLinkServer> servers = serverConfig.selectAll();
        LOGGER.info("Testlink : checking update from servers {}", servers.size());
        for (final TestLinkServer testlinkServer : servers) {
            checkTestLinkServer(testlinkServer);
        }
    }
    
    
    /**
     * Checks the testlink server.
     * 
     * @param testlinkServer
     *            the testlink server.
     */
    public void checkTestLinkServer(final TestLinkServer testlinkServer) {
    
    
        LOGGER.info("Testlink : checking update from server {}.", testlinkServer.getName());
        try {
            final ITestLinkServerProxy openProxy = proxyFactory.newConnector(testlinkServer);
            
            // traitement spécifique au plugin
            if (openProxy != null) {
                final List<TestLinkProject> listProject = openProxy.getListProject();
                for (final TestLinkProject projet : listProject) {
                    LOGGER.info("Testlink : checking update from project {} of server {}.",
                            projet.getName(), testlinkServer.getName());
                    analysisService.checkTestlinkProject(openProxy, projet);
                }
            }
        } catch (final Exception e) {
            LOGGER.error("Testlink update failed for server {}", testlinkServer.getName(), e);
        }
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(final JobExecutionContext _context) throws JobExecutionException {
    
    
        checkServers();
        
    }
    
    
    public ITestLinkAlertFactory getAlertFactory() {
    
    
        return alertFactory;
    }
    
    
    public IEventPushService getAlertService() {
    
    
        return alertService;
    }
    
    
    public ITestLinkServerDAO getServerConfig() {
    
    
        return serverConfig;
    }
    
    
    public void setAlertFactory(final ITestLinkAlertFactory alertFactory) {
    
    
        this.alertFactory = alertFactory;
    }
    
    
    public void setAlertService(final IEventPushService alertService) {
    
    
        this.alertService = alertService;
    }
    
    
    public void setServerConfig(final ITestLinkServerDAO serverConfig) {
    
    
        this.serverConfig = serverConfig;
    }
    
}
