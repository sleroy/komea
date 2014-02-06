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
 *
 * @author rgalerme
 */
public class TestLinkCheckerBean {

    @Autowired
    private IAlertPushService alertService;

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
            List<TestLinkProject> listProject = openProxy.getListProject();
            for (TestLinkProject projet : listProject) {

                // solution lourde et lente qui permet d'avoir le detail de chaque test
                List<TestLinkTestCase> totalTests = openProxy.getTotalTests(projet);
                TestFilter testFilter = TestFilter.filter(totalTests);
                this.alertService.sendEvent(this.alertFactory.newTotalTests(totalTests.size(), projet.getName()));
                this.alertService.sendEvent(this.alertFactory.newBlockedTests(testFilter.getBlockedTests().size(), projet.getName()));
                this.alertService.sendEvent(this.alertFactory.newFailedTests(testFilter.getFailedTests().size(), projet.getName()));
                this.alertService.sendEvent(this.alertFactory.newSuccessfultest(testFilter.getSuccessfulTests().size(), projet.getName()));
                this.alertService.sendEvent(this.alertFactory.newUnexecutedTest(testFilter.getUnexecutedTests().size(), projet.getName()));
                this.alertService.sendEvent(this.alertFactory.newUnassociedTest(testFilter.getUnassociedTest().size(), projet.getName()));
//                 

                //solution legère et rapide mais qui permet pas d'avoir le detail des tests
//                MetricTest metricTest = openProxy.getMetricTest(projet);
//                this.alertService.sendEvent(this.alertFactory.newTotalTests(metricTest.getNumberTest(), projet.getName()));
//                this.alertService.sendEvent(this.alertFactory.newBlockedTests(metricTest.getBlockedTest(), projet.getName()));
//                this.alertService.sendEvent(this.alertFactory.newFailedTests(metricTest.getFailedTest(), projet.getName()));
//                this.alertService.sendEvent(this.alertFactory.newSuccessfultest(metricTest.getSuccessfulTest(), projet.getName()));
//                this.alertService.sendEvent(this.alertFactory.newUnexecutedTest(metricTest.getUnexecutedTest(), projet.getName()));
                //Requirement
//                List<TestLinkRequirement> requirements = openProxy.getRequirements(projet);
//                RequirementFilter requirementFilter = RequirementFilter.filter(requirements);
//                this.alertService.sendEvent(this.alertFactory.newRequirements(requirements.size(), projet.getName()));
//                this.alertService.sendEvent(this.alertFactory.newTested(requirementFilter.getTestedRequirements(projet.getName()).size(), projet.getName()));
//                this.alertService.sendEvent(this.alertFactory.newUntested(requirementFilter.getUntestedRequirements(projet.getName()).size(), projet.getName()));
            }

        }
    }

    public IAlertPushService  getAlertService() {
        return alertService;
    }

    public void setAlertService(IAlertPushService  alertService) {
        this.alertService = alertService;
    }

    public ITestLinkAlertFactory getAlertFactory() {
        return alertFactory;
    }

    public void setAlertFactory(ITestLinkAlertFactory alertFactory) {
        this.alertFactory = alertFactory;
    }

    public ITestLinkServerManagerService getServerConfig() {
        return serverConfig;
    }

    public void setServerConfig(ITestLinkServerManagerService serverConfig) {
        this.serverConfig = serverConfig;
    }

}
