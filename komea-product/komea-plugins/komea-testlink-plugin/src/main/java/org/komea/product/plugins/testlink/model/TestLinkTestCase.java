/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.testlink.model;



/**
 * @author rgalerme
 */
public class TestLinkTestCase
{
    
    
    private TestCaseStatus executionStatus;
    private int            id;
    
    
    
    public TestLinkTestCase() {
    
    
    }
    
    
    public TestLinkTestCase(final int id, final TestCaseStatus executionStatus) {
    
    
        this.id = id;
        this.executionStatus = executionStatus;
    }
    
    
    public TestCaseStatus getExecutionStatus() {
    
    
        return executionStatus;
    }
    
    
    public int getId() {
    
    
        return id;
    }
    
    
    public void setExecutionStatus(final TestCaseStatus executionStatus) {
    
    
        this.executionStatus = executionStatus;
    }
    
    
    public void setId(final int id) {
    
    
        this.id = id;
    }
    
}
