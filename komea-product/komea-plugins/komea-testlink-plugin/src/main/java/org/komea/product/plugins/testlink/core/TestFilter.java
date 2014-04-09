/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.testlink.core;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rgalerme
 */
public class TestFilter {

    private final List<TestLinkTestCase> failedTest;
    private final List<TestLinkTestCase> totalTest;
    private final List<TestLinkTestCase> unexecutedTest;
    private final List<TestLinkTestCase> blockedTest;
    private final List<TestLinkTestCase> unassociedTest;
    private final List<TestLinkTestCase> successfulTest;

    public static TestFilter filter(List<TestLinkTestCase> totalTests) {
        TestFilter filter = new TestFilter(totalTests);
        filter.updateData();
        return filter;
    }

    private TestFilter(List<TestLinkTestCase> totalTests) {
        this.totalTest = totalTests;
        this.failedTest = new ArrayList<TestLinkTestCase>();
        this.unexecutedTest = new ArrayList<TestLinkTestCase>();
        this.blockedTest = new ArrayList<TestLinkTestCase>();
        this.successfulTest = new ArrayList<TestLinkTestCase>();
        this.unassociedTest = new ArrayList<TestLinkTestCase>();
    }

    private void updateData() {
        for (TestLinkTestCase testLinkTestCase : this.totalTest) {

            if (testLinkTestCase.getExecutionStatus().equals("p")) {
                this.successfulTest.add(testLinkTestCase);
            } else if (testLinkTestCase.getExecutionStatus().equals("n")) {
                this.unexecutedTest.add(testLinkTestCase);
            } else if (testLinkTestCase.getExecutionStatus().equals("b")) {
                this.blockedTest.add(testLinkTestCase);
            } else if (testLinkTestCase.getExecutionStatus().equals("f")) {
                this.failedTest.add(testLinkTestCase);
            } else if (testLinkTestCase.getExecutionStatus().equals("u")) {
                this.unassociedTest.add(testLinkTestCase);
            }

        }
    }

    public List<TestLinkTestCase> getUnassociedTest() {
        return unassociedTest;
    }

    public List<TestLinkTestCase> getSuccessfulTests() {
        return this.successfulTest;
    }

    public List<TestLinkTestCase> getFailedTests() {
        return this.failedTest;
    }

    public List<TestLinkTestCase> getUnexecutedTests() {
        return this.unexecutedTest;
    }

    public List<TestLinkTestCase> getBlockedTests() {
        return this.blockedTest;
    }
}
