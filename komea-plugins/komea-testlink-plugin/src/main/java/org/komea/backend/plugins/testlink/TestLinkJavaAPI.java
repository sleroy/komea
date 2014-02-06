/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 To get your ApiKey Testlink must have the API enabled in the file custom_config.inc.php:

 $tlCfg->api->enabled = TRUE;
 (N.B. the documentation has a defect, the above syntax is correct)

 Then, after logging in click on the Link marked Personal in the top menu bar. This brings up a list of things, including a section marked API interface. That section contains the API Access Key.
 */
package org.komea.backend.plugins.testlink;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.constants.TestCaseDetails;
import br.eti.kinoshita.testlinkjavaapi.model.Build;
import br.eti.kinoshita.testlinkjavaapi.model.Execution;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import br.eti.kinoshita.testlinkjavaapi.model.TestPlan;
import br.eti.kinoshita.testlinkjavaapi.model.TestProject;
import br.eti.kinoshita.testlinkjavaapi.model.TestSuite;
import br.eti.kinoshita.testlinkjavaapi.util.TestLinkAPIException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.komea.backend.plugins.testlink.api.ITestLinkServerProxy;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rgalerme
 */
public class TestLinkJavaAPI implements ITestLinkServerProxy {

    private TestLinkAPI api = null;

    public void connexion(TestLinkServer server) {

        String url = server.getAddress();
        String devKey = server.getKey();
//        String url = "http://ares.tocea/testlink/lib/api/xmlrpc/v1/xmlrpc.php";
//        String devKey = "2dec70df08045278463817fb15d79c4d";
        URL testlinkURL = null;
        try {
            testlinkURL = new URL(url);
        } catch (MalformedURLException mue) {
            Logger.getLogger(TestLinkJavaAPI.class.getName()).log(Level.SEVERE, null, mue);
        }

        try {
            api = new TestLinkAPI(testlinkURL, devKey);

        } catch (TestLinkAPIException te) {
            Logger.getLogger(TestLinkJavaAPI.class.getName()).log(Level.SEVERE, null, te);
        }
    }

    @Override
    public List<TestLinkProject> getListProject() {

        List<TestLinkProject> result = new ArrayList<TestLinkProject>();
        if (api != null) {
            TestProject[] projects = api.getProjects();
            for (TestProject testProject : projects) {
                result.add(new TestLinkProject(testProject.getId(), testProject.getName()));
            }
        } else {

            Logger.getLogger(TestLinkJavaAPI.class.getName()).log(Level.SEVERE, null, "Error, server not connected");
        }
        return result;
    }

    @Override
    public MetricTest getMetricTest(TestLinkProject _project) {
        MetricTest result = new MetricTest();
        TestPlan[] projectTestPlans = api.getProjectTestPlans(_project.getId());
        for (TestPlan testPlan : projectTestPlans) {
            Map<String, Object> totalsForTestPlan = api.getTotalsForTestPlan(testPlan.getId());
            recursiveMap(totalsForTestPlan, result, true);
        }
        return result;

    }

    private long getV193(String val, Map<String, Object> mapParc) {
        long result = -1;
        Object get = mapParc.get(val);
        if (get != null) {
            if (get instanceof Map) {
                Map<String, Object> res = (Map<String, Object>) get;
                Object get1 = res.get("qty");
                if (get1 instanceof Integer) {
                    result = ((Integer) get1).longValue();
                }
            }
        }
        return result;
    }

    private void recursiveMap(Map<String, Object> mapParc, MetricTest result, boolean condContinuer) {
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

            Collection<Object> values = mapParc.values();
            for (Object object : values) {

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

    @Override
    public List<TestLinkTestCase> getTotalTests(TestLinkProject _project) {

        List<TestLinkTestCase> result = new ArrayList<TestLinkTestCase>();
//        if (_project.getName().equals("sonar Scertify plugin")) {

            if (api != null) {
                TestPlan[] projectTestPlans = api.getProjectTestPlans(_project.getId());
                for (TestPlan testPlan : projectTestPlans) {
                    TestSuite[] testSuitesForTestPlan = api.getTestSuitesForTestPlan(testPlan.getId());
                    for (TestSuite testSuite : testSuitesForTestPlan) {

                        TestCase[] testCasesForTestSuite = api.getTestCasesForTestSuite(testSuite.getId(), Boolean.TRUE, TestCaseDetails.FULL);
                        for (TestCase testCase : testCasesForTestSuite) {
//                        TestCase testCase1 = api.getTestCase(testCase.getId(), 1, testCase.getVersionId());
                            String status = "u";
                            try {
                               
                                Execution lastExecutionResult = api.getLastExecutionResult(testPlan.getId(), testCase.getId(), Integer.valueOf(1));
                                if (lastExecutionResult != null && lastExecutionResult.getStatus() != null) {
                                    status = lastExecutionResult.getStatus().toString();
                                }else
                                {
                                status = "n";
                                }
                            } catch (TestLinkAPIException e) {
                            // test non associe a un plan
//                                System.out.println("de");

                            }
                            result.add(new TestLinkTestCase(testCase.getId().intValue(), status));
                        }
                    }
                }

            } else {

                Logger.getLogger(TestLinkJavaAPI.class.getName()).log(Level.SEVERE, null, "Error, server not connected");
            }
//        }
        return result;
    }

    @Override
    public List<TestLinkRequirement> getRequirements(TestLinkProject _project) {
//        return this.totalRequirements;
        return null;
    }

}
