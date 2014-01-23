/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.bugzilla;

/**
 *
 * @author rgalerme
 */
public class BugzillaBug {

    private int id;
    private String Status;

    public BugzillaBug(int id, String Status) {
        this.id = id;
        this.Status = Status;
    }

    public BugzillaBug() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

}
