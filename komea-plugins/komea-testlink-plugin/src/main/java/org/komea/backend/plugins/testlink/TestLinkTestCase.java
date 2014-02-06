/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.testlink;

/**
 *
 * @author rgalerme
 */
public class TestLinkTestCase {

    private int id;
    private String executionStatus;

    public TestLinkTestCase(int id, String executionStatus) {
        this.id = id;
        this.executionStatus = executionStatus;
    }

    public TestLinkTestCase() {
    }

    public String getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(String executionStatus) {
        this.executionStatus = executionStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
