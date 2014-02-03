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
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import br.eti.kinoshita.testlinkjavaapi.model.TestPlan;
import br.eti.kinoshita.testlinkjavaapi.model.TestProject;
import br.eti.kinoshita.testlinkjavaapi.model.TestSuite;
import br.eti.kinoshita.testlinkjavaapi.util.TestLinkAPIException;
import java.net.MalformedURLException;
import java.net.URL;
import org.komea.backend.plugins.testlink.api.ITestLinkServerProxy;

/**
 *
 * @author rgalerme
 */
public class TestLinkJavaAPI implements ITestLinkServerProxy {

    
    private void getTotaltests() {
                String url = "http://127.0.0.1:8080/testlink/lib/api/xmlrpc/v1/xmlrpc.php";
        String devKey = "cf86c231b7c909f15b4d61e12ace5507";
//         String devKey =  "f3c93d891a6102d373a3578c085ea7102235e12e9e50f371aeb33524aa2c855b";
        TestLinkAPI api = null;

        URL testlinkURL = null;

        try {
            testlinkURL = new URL(url);
        } catch (MalformedURLException mue) {
            mue.printStackTrace(System.err);
            System.exit(-1);
        }

        try {
            api = new TestLinkAPI(testlinkURL, devKey);

        } catch (TestLinkAPIException te) {
            te.printStackTrace(System.err);
            System.exit(-1);
        }
//        new TestLinkAPI
        TestProject[] projects = api.getProjects();
        TestPlan[] projectTestPlans = api.getProjectTestPlans(Integer.MIN_VALUE);
        for (TestPlan testPlan : projectTestPlans) {
//            testPlan.
        }
        TestSuite[] testSuitesForTestPlan = api.getTestSuitesForTestPlan(Integer.MIN_VALUE);
        for (TestSuite testSuite : testSuitesForTestPlan) {
           
        }
        TestCase[] testCasesForTestSuite = api.getTestCasesForTestSuite(Integer.SIZE, Boolean.TRUE, TestCaseDetails.SIMPLE);
        for (TestCase testCase : testCasesForTestSuite) {
            
        }
        System.out.println(api.ping());
    }

}
