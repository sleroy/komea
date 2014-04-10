/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * To get your ApiKey Testlink must have the API enabled in the file custom_config.inc.php:
 * $tlCfg->api->enabled = TRUE;
 * (N.B. the documentation has a defect, the above syntax is correct)
 * Then, after logging in click on the Link marked Personal in the top menu bar. This brings up a list of things, including a section marked
 * API interface. That section contains the API Access Key.
 */

package org.komea.product.plugins.testlink.business;



import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.komea.product.plugins.testlink.api.ITestLinkServerProxy;
import org.komea.product.plugins.testlink.api.TestLinkException;
import org.komea.product.plugins.testlink.model.TestCaseStatus;
import org.komea.product.plugins.testlink.model.TestLinkMetrics;
import org.komea.product.plugins.testlink.model.TestLinkProject;
import org.komea.product.plugins.testlink.model.TestLinkRequirement;
import org.komea.product.plugins.testlink.model.TestLinkServer;
import org.komea.product.plugins.testlink.model.TestLinkTestCase;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionStatus;
import br.eti.kinoshita.testlinkjavaapi.constants.TestCaseDetails;
import br.eti.kinoshita.testlinkjavaapi.model.Execution;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import br.eti.kinoshita.testlinkjavaapi.model.TestPlan;
import br.eti.kinoshita.testlinkjavaapi.model.TestProject;
import br.eti.kinoshita.testlinkjavaapi.model.TestSuite;
import br.eti.kinoshita.testlinkjavaapi.util.TestLinkAPIException;



/**
 * This class is the implementation of the proxy that obtains informations from Testlink.
 * 
 * @author rgalerme
 */
public class TestLinkJavaAPI implements ITestLinkServerProxy
{
    
    
    private TestLinkAPI api = null;
    
    
    
    /**
     * Connexion to the testlink server
     * 
     * @param _server
     *            the server
     */
    public void connexion(final TestLinkServer _server) {
    
    
        Validate.notNull(_server);
        
        final String url = _server.getAddress();
        final String devKey = _server.getKey();
        Validate.notNull(url, "url must be provided.");
        Validate.notNull(devKey, "developer key is required.");
        URL testlinkURL = null;
        try {
            testlinkURL = new URL(url);
            api = new TestLinkAPI(testlinkURL, devKey);
            
        } catch (final MalformedURLException mue) {
            throw new TestLinkException(
                    "Invalid url is provided for the configuration of the testlink server "
                            + _server.getName(), mue);
            
            
        } catch (final Exception te) {
            throw new TestLinkException("Connexion with the server "
                    + _server.getName() + " was impossible for the following reason :  "
                    + te.getMessage(), te);
        }
    }
    
    
    /**
     * Convert execution status into testlink status
     * 
     * @param lastExecutionResult
     *            the last execution result.
     * @return the test case status.
     */
    public TestCaseStatus convertExecutionStatusIntoTestlinkStatus(
            final ExecutionStatus lastExecutionResult) {
    
    
        TestCaseStatus status = null;
        switch (lastExecutionResult) {
            case BLOCKED:
                status = TestCaseStatus.BLOCKED;
                break;
            case FAILED:
                status = TestCaseStatus.FAILED;
                break;
            case NOT_RUN:
                status = TestCaseStatus.UNEXECUTED;
                break;
            case PASSED:
                status = TestCaseStatus.SUCCESSFUL;
                break;
        
        }
        return status;
    }
    
    
    public TestLinkAPI getApi() {
    
    
        return api;
    }
    
    
    /**
     * Returns the last execution result from API.
     * 
     * @param testPlan
     * @param testCase
     * @return
     */
    public Execution getLastExecutionResult(final TestPlan testPlan, final TestCase testCase) {
    
    
        return api.getLastExecutionResult(testPlan.getId(), testCase.getId(), Integer.valueOf(1));
    }
    
    
    @Override
    public List<TestLinkProject> getListProject() {
    
    
        final List<TestLinkProject> result = new ArrayList<TestLinkProject>(50);
        
        final TestProject[] projects = api.getProjects();
        for (final TestProject testProject : projects) {
            result.add(new TestLinkProject(testProject.getId(), testProject.getName()));
        }
        
        return result;
    }
    
    
    @Override
    public TestLinkMetrics getMetricTest(final TestLinkProject _project) {
    
    
        final TestLinkMetrics result = new TestLinkMetrics();
        final TestPlan[] projectTestPlans = api.getProjectTestPlans(_project.getId());
        for (final TestPlan testPlan : projectTestPlans) {
            final Map<String, Object> totalsForTestPlan =
                    api.getTotalsForTestPlan(testPlan.getId());
            recursiveMap(totalsForTestPlan, result, true);
        }
        return result;
        
    }
    
    
    @Override
    public List<TestLinkRequirement> getRequirements(final TestLinkProject _project) {
    
    
        // return this.totalRequirements;
        return Collections.EMPTY_LIST;
    }
    
    
    /**
     * Gets total test for a test case
     * 
     * @param result
     *            the jresult
     * @param testPlan
     *            the test plan
     * @param testCase
     *            the test case.
     */
    public void getTotalTestForATestCase(
            final List<TestLinkTestCase> result,
            final TestPlan testPlan,
            final TestCase testCase) {
    
    
        // TestCase testCase1 = api.getTestCase(testCase.getId(), 1, testCase.getVersionId());
        TestCaseStatus status = TestCaseStatus.UNEXECUTED;
        try {
            // la valeur de l'id externe est mise de facon arbitraire, si problème utilise une version personalise de l'api
            // pour la recuperer
            final Execution lastExecutionResult = getLastExecutionResult(testPlan, testCase);
            if (hasExecutionResult(lastExecutionResult)) {
                status = convertExecutionStatusIntoTestlinkStatus(lastExecutionResult.getStatus());
            } else {
                status = TestCaseStatus.UNEXECUTED;
            }
        } catch (final TestLinkAPIException e) {
            // test non associe a un plan
            // System.out.println("de");
            
        }
        result.add(new TestLinkTestCase(testCase.getId().intValue(), status));
    }
    
    
    public void getTotalTestForATestPlan(
            final List<TestLinkTestCase> result,
            final TestPlan testPlan) {
    
    
        final TestSuite[] testSuitesForTestPlan = api.getTestSuitesForTestPlan(testPlan.getId());
        for (final TestSuite testSuite : testSuitesForTestPlan) {
            
            getTotalTestForATestSuite(result, testPlan, testSuite);
        }
    }
    
    
    public void getTotalTestForATestSuite(
            final List<TestLinkTestCase> result,
            final TestPlan testPlan,
            final TestSuite testSuite) {
    
    
        final TestCase[] testCasesForTestSuite =
                api.getTestCasesForTestSuite(testSuite.getId(), Boolean.TRUE, TestCaseDetails.FULL);
        for (final TestCase testCase : testCasesForTestSuite) {
            getTotalTestForATestCase(result, testPlan, testCase);
        }
    }
    
    
    @Override
    public List<TestLinkTestCase> getTotalTests(final TestLinkProject _project) {
    
    
        final List<TestLinkTestCase> result = new ArrayList<TestLinkTestCase>();
        
        Validate.notNull(api);
        final TestPlan[] projectTestPlans = api.getProjectTestPlans(_project.getId());
        for (final TestPlan testPlan : projectTestPlans) {
            getTotalTestForATestPlan(result, testPlan);
            
            
        }
        return result;
    }
    
    
    public boolean hasExecutionResult(final Execution lastExecutionResult) {
    
    
        return lastExecutionResult != null && lastExecutionResult.getStatus() != null;
    }
    
    
    public void setApi(final TestLinkAPI _api) {
    
    
        api = _api;
    }
    
    
    private long getV193(final String val, final Map<String, Object> mapParc) {
    
    
        long result = -1;
        final Object get = mapParc.get(val);
        if (get != null) {
            if (get instanceof Map) {
                final Map<String, Object> res = (Map<String, Object>) get;
                final Object get1 = res.get("qty");
                if (get1 instanceof Integer) {
                    result = ((Integer) get1).longValue();
                }
            }
        }
        return result;
    }
    
    
    private void recursiveMap(
            final Map<String, Object> mapParc,
            final TestLinkMetrics result,
            final boolean condContinuer) {
    
    
        boolean continuer = condContinuer;
        long blocked = 0, passed = 0, failed = 0, notRun = 0;
        
        blocked = getV193("blocked", mapParc);
        if (blocked != -1) {
            passed = getV193("passed", mapParc);
            if (passed != -1) {
                failed = getV193("failed", mapParc);
                if (failed != -1) {
                    notRun = getV193("not_run", mapParc);
                    if (notRun != -1) {
                        continuer = false;
                    }
                }
            }
        }
        if (continuer) {
            
            final Collection<Object> values = mapParc.values();
            for (final Object object : values) {
                
                if (object instanceof Map) {
                    recursiveMap((Map<String, Object>) object, result, continuer);
                }
            }
        } else {
            result.setBlockedTest(blocked);
            result.setFailedTest(failed);
            result.setSuccessfulTest(passed);
            result.setUnexecutedTest(notRun);
            result.setNumberTest(notRun + blocked + failed + passed);
        }
    }
    
}
