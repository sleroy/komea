/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.testlink.core;

/**
 *
 * @author rgalerme
 */
public class MetricTest {

    private long numberTest;
    private long blockedTest;
    private long failedTest;
    private long successfulTest;
    private long unexecutedTest;

    public MetricTest(long numberTest, long blockedTest, long failedTest, long successfulTest, long unexecutedTest) {
        this.numberTest = numberTest;
        this.blockedTest = blockedTest;
        this.failedTest = failedTest;
        this.successfulTest = successfulTest;
        this.unexecutedTest = unexecutedTest;
    }

    public MetricTest() {
    }
    
    

    public void setNumberTest(long numberTest) {
        this.numberTest = numberTest;
    }

    public void setBlockedTest(long blockedTest) {
        this.blockedTest = blockedTest;
    }

    public void setFailedTest(long failedTest) {
        this.failedTest = failedTest;
    }

    public void setSuccessfulTest(long successfulTest) {
        this.successfulTest = successfulTest;
    }

    public void setUnexecutedTest(long unexecutedTest) {
        this.unexecutedTest = unexecutedTest;
    }

    
    
    public long getNumberTest() {
        return numberTest;
    }

    public long getBlockedTest() {
        return blockedTest;
    }

    public long getFailedTest() {
        return failedTest;
    }

    public long getSuccessfulTest() {
        return successfulTest;
    }

    public long getUnexecutedTest() {
        return unexecutedTest;
    }

}
