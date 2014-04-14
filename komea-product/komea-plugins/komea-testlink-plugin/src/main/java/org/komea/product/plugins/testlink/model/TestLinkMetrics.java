/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.testlink.model;



/**
 * This class defines the metrics that may be computed from testlink data.
 * 
 * @author rgalerme
 */
public class TestLinkMetrics
{
    
    
    private long blockedTest    = 0L;
    private long failedTest     = 0L;
    private long numberTest     = 0L;
    private long successfulTest = 0L;
    private long unexecutedTest = 0L;
    
    
    
    public TestLinkMetrics() {
    
    
        super();
        
    }
    
    
    public TestLinkMetrics(
            final long numberTest,
            final long blockedTest,
            final long failedTest,
            final long successfulTest,
            final long unexecutedTest) {
    
    
        this.numberTest = numberTest;
        this.blockedTest = blockedTest;
        this.failedTest = failedTest;
        this.successfulTest = successfulTest;
        this.unexecutedTest = unexecutedTest;
    }
    
    
    public long getBlockedTest() {
    
    
        return blockedTest;
    }
    
    
    public long getFailedTest() {
    
    
        return failedTest;
    }
    
    
    public long getNumberTest() {
    
    
        return numberTest;
    }
    
    
    public long getSuccessfulTest() {
    
    
        return successfulTest;
    }
    
    
    public long getUnexecutedTest() {
    
    
        return unexecutedTest;
    }
    
    
    public void setBlockedTest(final long blockedTest) {
    
    
        this.blockedTest = blockedTest;
    }
    
    
    public void setFailedTest(final long failedTest) {
    
    
        this.failedTest = failedTest;
    }
    
    
    public void setNumberTest(final long numberTest) {
    
    
        this.numberTest = numberTest;
    }
    
    
    public void setSuccessfulTest(final long successfulTest) {
    
    
        this.successfulTest = successfulTest;
    }
    
    
    public void setUnexecutedTest(final long unexecutedTest) {
    
    
        this.unexecutedTest = unexecutedTest;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "TestLinkMetrics [blockedTest="
                + blockedTest + ", failedTest=" + failedTest + ", numberTest=" + numberTest
                + ", successfulTest=" + successfulTest + ", unexecutedTest=" + unexecutedTest + "]";
    }
    
}
