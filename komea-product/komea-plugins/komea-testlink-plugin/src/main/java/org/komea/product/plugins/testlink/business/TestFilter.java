/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.testlink.business;



import java.util.ArrayList;
import java.util.List;

import org.komea.product.plugins.testlink.model.TestLinkTestCase;



/**
 * This class offers the possibility to obtain the list of tests inside testlink.
 * 
 * @author rgalerme
 */
public class TestFilter
{
    
    
    /**
     * Build a test filter
     * 
     * @param totalTests
     *            the list of test to obtain
     * @return the test filter;
     */
    public static TestFilter filter(final List<TestLinkTestCase> totalTests) {
    
    
        final TestFilter filter = new TestFilter(totalTests);
        filter.updateData();
        return filter;
    }
    
    
    
    private final List<TestLinkTestCase> blockedTest;
    private final List<TestLinkTestCase> failedTest;
    private final List<TestLinkTestCase> successfulTest;
    private final List<TestLinkTestCase> totalTest;
    private final List<TestLinkTestCase> unassociedTest;
    
    
    private final List<TestLinkTestCase> unexecutedTest;
    
    
    
    private TestFilter(final List<TestLinkTestCase> totalTests) {
    
    
        totalTest = totalTests;
        failedTest = new ArrayList<TestLinkTestCase>();
        unexecutedTest = new ArrayList<TestLinkTestCase>();
        blockedTest = new ArrayList<TestLinkTestCase>();
        successfulTest = new ArrayList<TestLinkTestCase>();
        unassociedTest = new ArrayList<TestLinkTestCase>();
    }
    
    
    public List<TestLinkTestCase> getBlockedTests() {
    
    
        return blockedTest;
    }
    
    
    public List<TestLinkTestCase> getFailedTests() {
    
    
        return failedTest;
    }
    
    
    public List<TestLinkTestCase> getSuccessfulTests() {
    
    
        return successfulTest;
    }
    
    
    public List<TestLinkTestCase> getTotalTest() {
    
    
        return totalTest;
    }
    
    
    public List<TestLinkTestCase> getUnassociedTest() {
    
    
        return unassociedTest;
    }
    
    
    public List<TestLinkTestCase> getUnexecutedTests() {
    
    
        return unexecutedTest;
    }
    
    
    private void updateData() {
    
    
        for (final TestLinkTestCase testLinkTestCase : totalTest) {
            switch (testLinkTestCase.getExecutionStatus()) {
                case SUCCESSFUL:
                    successfulTest.add(testLinkTestCase);
                    break;
                case UNEXECUTED:
                    unexecutedTest.add(testLinkTestCase);
                    break;
                case BLOCKED:
                    blockedTest.add(testLinkTestCase);
                    break;
                case FAILED:
                    failedTest.add(testLinkTestCase);
                    break;
                case UNASSOCIATED:
                    unassociedTest.add(testLinkTestCase);
                    break;
            }
            
        }
    }
}
