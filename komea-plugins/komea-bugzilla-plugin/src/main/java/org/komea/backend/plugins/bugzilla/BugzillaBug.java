/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.backend.plugins.bugzilla;



import java.util.Date;



/**
 * @author rgalerme
 */
public class BugzillaBug
{
    
    
    private int     id;
    private String  Status;
    private boolean is_open;
    private Date    date_creation;
    private boolean is_assigned;
    
    
    
    public BugzillaBug() {
    
    
    }
    
    
    public BugzillaBug(
            final int id,
            final String Status,
            final boolean is_open,
            final Date date_creation,
            final boolean is_assigned) {
    
    
        this.id = id;
        this.Status = Status;
        this.is_open = is_open;
        this.date_creation = date_creation;
        this.is_assigned = is_assigned;
    }
    
    
    public Date getDate_creation() {
    
    
        return date_creation;
    }
    
    
    public int getId() {
    
    
        return id;
    }
    
    
    public String getStatus() {
    
    
        return Status;
    }
    
    
    public boolean isIs_assigned() {
    
    
        return is_assigned;
    }
    
    
    public boolean isIs_open() {
    
    
        return is_open;
    }
    
    
    public void setDate_creation(final Date date_creation) {
    
    
        this.date_creation = date_creation;
    }
    
    
    public void setId(final int id) {
    
    
        this.id = id;
    }
    
    
    public void setIs_assigned(final boolean is_assigned) {
    
    
        this.is_assigned = is_assigned;
    }
    
    
    public void setIs_open(final boolean is_open) {
    
    
        this.is_open = is_open;
    }
    
    
    public void setStatus(final String Status) {
    
    
        this.Status = Status;
    }
    
}
