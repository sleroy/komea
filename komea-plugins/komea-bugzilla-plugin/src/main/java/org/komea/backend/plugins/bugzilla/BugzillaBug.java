/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.bugzilla;

import java.util.Date;

/**
 *
 * @author rgalerme
 */
public class BugzillaBug {

    private int id;
    private String Status;
    private boolean is_open;
    private Date date_creation;
    private boolean is_assigned;

    public BugzillaBug(int id, String Status, boolean is_open, Date date_creation, boolean is_assigned) {
        this.id = id;
        this.Status = Status;
        this.is_open = is_open;
        this.date_creation = date_creation;
        this.is_assigned = is_assigned;
    }

    public boolean isIs_assigned() {
        return is_assigned;
    }

    public void setIs_assigned(boolean is_assigned) {
        this.is_assigned = is_assigned;
    }

    public boolean isIs_open() {
        return is_open;
    }

    public void setIs_open(boolean is_open) {
        this.is_open = is_open;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
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
