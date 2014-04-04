/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.testlink.core;



import java.util.List;

import org.komea.product.plugins.testlink.api.ITestLinkAlertFactory;
import org.komea.product.plugins.testlink.api.ITestLinkServerConfiguration;
import org.komea.product.plugins.testlink.api.ITestLinkServerService;
import org.komea.product.plugins.testlink.api.ITestLinkServerProxy;
import org.komea.product.backend.service.esper.IEventPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;



/**
 * @author rgalerme
 */
@Service
public class TestLinkCheckerBean
{
    
    
    @Autowired
    private IEventPushService             alertService;
    
    @Autowired
    private ITestLinkAlertFactory         alertFactory;
    
    @Autowired
    private ITestLinkServerService serverConfig;
    
    
    
    @Scheduled(fixedDelay = 10)
    public void checkServers() {
    
    
        final List<ITestLinkServerConfiguration> servers = serverConfig.getServers();
        for (final ITestLinkServerConfiguration testlinkServer : servers) {
            final ITestLinkServerProxy openProxy = testlinkServer.openProxy();
            // traitement spécifique au plugin
            final List<TestLinkProject> listProject = openProxy.getListProject();
            for (final TestLinkProject projet : listProject) {
                
                // solution lourde et lente qui permet d'avoir le detail de chaque test
                final List<TestLinkTestCase> totalTests = openProxy.getTotalTests(projet);
                final TestFilter testFilter = TestFilter.filter(totalTests);
                alertService.sendEventDto(alertFactory.newTotalTests(totalTests.size(),
                        projet.getName()));
                alertService.sendEventDto(alertFactory.newBlockedTests(testFilter.getBlockedTests()
                        .size(), projet.getName()));
                alertService.sendEventDto(alertFactory.newFailedTests(testFilter.getFailedTests()
                        .size(), projet.getName()));
                alertService.sendEventDto(alertFactory.newSuccessfultest(testFilter
                        .getSuccessfulTests().size(), projet.getName()));
                alertService.sendEventDto(alertFactory.newUnexecutedTest(testFilter
                        .getUnexecutedTests().size(), projet.getName()));
                alertService.sendEventDto(alertFactory.newUnassociedTest(testFilter
                        .getUnassociedTest().size(), projet.getName()));
                //
                
                // solution legère et rapide mais qui permet pas d'avoir le detail des tests
                
                // MetricTest metricTest = openProxy.getMetricTest(projet);
                // this.alertService.sendEvent(this.alertFactory.newTotalTests(metricTest.getNumberTest(), projet.getName()));
                // this.alertService.sendEvent(this.alertFactory.newBlockedTests(metricTest.getBlockedTest(), projet.getName()));
                // this.alertService.sendEvent(this.alertFactory.newFailedTests(metricTest.getFailedTest(), projet.getName()));
                // this.alertService.sendEvent(this.alertFactory.newSuccessfultest(metricTest.getSuccessfulTest(), projet.getName()));
                // this.alertService.sendEvent(this.alertFactory.newUnexecutedTest(metricTest.getUnexecutedTest(), projet.getName()));
                // Requirement
                // List<TestLinkRequirement> requirements = openProxy.getRequirements(projet);
                // RequirementFilter requirementFilter = RequirementFilter.filter(requirements);
                // this.alertService.sendEvent(this.alertFactory.newRequirements(requirements.size(), projet.getName()));
                // this.alertService.sendEvent(this.alertFactory.newTested(requirementFilter.getTestedRequirements(projet.getName()).size(),
                // projet.getName()));
                // this.alertService.sendEvent(this.alertFactory.newUntested(requirementFilter.getUntestedRequirements(projet.getName()).size(),
                // projet.getName()));
            }
            
        }
    }
    
    
    public ITestLinkAlertFactory getAlertFactory() {
    
    
        return alertFactory;
    }
    
    
    public IEventPushService getAlertService() {
    
    
        return alertService;
    }
    
    
    public ITestLinkServerService getServerConfig() {
    
    
        return serverConfig;
    }
    
    
    public void setAlertFactory(final ITestLinkAlertFactory alertFactory) {
    
    
        this.alertFactory = alertFactory;
    }
    
    
    public void setAlertService(final IEventPushService alertService) {
    
    
        this.alertService = alertService;
    }
    
    
    public void setServerConfig(final ITestLinkServerService serverConfig) {
    
    
        this.serverConfig = serverConfig;
    }
    
}
