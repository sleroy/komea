/**
 * 
 */

package org.komea.product.plugins.testlink.core;



import java.util.List;

import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.plugins.testlink.api.ITestLinkAlertFactory;
import org.komea.product.plugins.testlink.api.ITestLinkAnalysisService;
import org.komea.product.plugins.testlink.api.ITestLinkServerDAO;
import org.komea.product.plugins.testlink.api.ITestLinkServerProxy;
import org.komea.product.plugins.testlink.api.ITestLinkServerProxyFactory;
import org.komea.product.plugins.testlink.business.TestFilter;
import org.komea.product.plugins.testlink.model.TestLinkProject;
import org.komea.product.plugins.testlink.model.TestLinkTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * @author sleroy
 */
@Service
public class TestLinkAnalysisService implements ITestLinkAnalysisService
{
    
    
    @Autowired
    private ITestLinkAlertFactory       alertFactory;
    
    @Autowired
    private IEventPushService           alertService;
    
    @Autowired
    private ITestLinkServerProxyFactory proxyFactory;
    
    @Autowired
    private ITestLinkServerDAO          serverConfig;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.testlink.core.ITestLinkAnalysisService#checkTestlinkProject(org.komea.product.plugins.testlink.api.
     * ITestLinkServerProxy, org.komea.product.plugins.testlink.model.TestLinkProject)
     */
    @Override
    public void checkTestlinkProject(
            final ITestLinkServerProxy openProxy,
            final TestLinkProject projet) {
    
    
        // solution lourde et lente qui permet d'avoir le detail de chaque test
        final List<TestLinkTestCase> totalTests = openProxy.getTotalTests(projet);
        final TestFilter testFilter = TestFilter.filter(totalTests);
        alertService.sendEventDto(alertFactory.newTotalTests(totalTests.size(), projet.getName()));
        alertService.sendEventDto(alertFactory.newBlockedTests(testFilter.getBlockedTests().size(),
                projet.getName()));
        alertService.sendEventDto(alertFactory.newFailedTests(testFilter.getFailedTests().size(),
                projet.getName()));
        alertService.sendEventDto(alertFactory.newSuccessfultest(testFilter.getSuccessfulTests()
                .size(), projet.getName()));
        alertService.sendEventDto(alertFactory.newUnexecutedTest(testFilter.getUnexecutedTests()
                .size(), projet.getName()));
        alertService.sendEventDto(alertFactory.newUnassociedTest(testFilter.getUnassociedTest()
                .size(), projet.getName()));
        //
        
        // solution legère et rapide mais qui permet pas d'avoir le detail des tests
        // TestLinkMetrics metricTest = openProxy.getMetricTest(projet);
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
