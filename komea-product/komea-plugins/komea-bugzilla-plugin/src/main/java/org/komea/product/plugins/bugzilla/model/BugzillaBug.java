/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.bugzilla.model;



import java.io.Serializable;
import java.util.Date;



/**
 * @author rgalerme
 * @version $Revision: 1.0 $
 */
public class BugzillaBug implements Serializable
{
    
    
    private Date    date_creation;
    private int     id;
    private boolean assigned;
    private boolean opened;
    private String  priority;
    private String  severity;
    private String  status;
    
    
    
    public BugzillaBug() {
    
    
    }
    
    
    /**
     * Constructor for BugzillaBug.
     * 
     * @param id
     *            int
     * @param status
     *            String
     * @param opened
     *            boolean
     * @param date_creation
     *            Date
     * @param assigned
     *            boolean
     * @param severity
     *            severity
     * @param priority
     *            priority
     */
    public BugzillaBug(
            final int id,
            final String status,
            final boolean is_open,
            final Date date_creation,
            final boolean is_assigned,
            final String severity,
            final String priority) {
    
    
        this.id = id;
        this.status = status;
        this.opened = is_open;
        this.date_creation = date_creation;
        this.assigned = is_assigned;
        this.severity = severity;
        this.priority = priority;
    }
    
    
    /**
     * Method getDate_creation.
     * 
     * @return Date
     */
    public Date getDate_creation() {
    
    
        return date_creation;
    }
    
    
    /**
     * Method getId.
     * 
     * @return int
     */
    public int getId() {
    
    
        return id;
    }
    
    
    public String getPriority() {
    
    
        return priority;
    }
    
    
    public String getSeverity() {
    
    
        return severity;
    }
    
    
    /**
     * Method getstatus.
     * 
     * @return String
     */
    public String getStatus() {
    
    
        return status;
    }
    
    
    /**
     * Method isIs_assigned.
     * 
     * @return boolean
     */
    public boolean isAssigned() {
    
    
        return assigned;
    }
    
    
    /**
     * Method isIs_open.
     * 
     * @return boolean
     */
    public boolean isOpened() {
    
    
        return opened;
    }
    
    
    /**
     * Method setDate_creation.
     * 
     * @param date_creation
     *            Date
     */
    public void setDate_creation(final Date date_creation) {
    
    
        this.date_creation = date_creation;
    }
    
    
    /**
     * Method setId.
     * 
     * @param id
     *            int
     */
    public void setId(final int id) {
    
    
        this.id = id;
    }
    
    
    /**
     * Method setIs_assigned.
     * 
     * @param assigned
     *            boolean
     */
    public void setAssigned(final boolean is_assigned) {
    
    
        this.assigned = is_assigned;
    }
    
    
    /**
     * Method setIs_open.
     * 
     * @param opened
     *            boolean
     */
    public void setOpened(final boolean is_open) {
    
    
        this.opened = is_open;
    }
    
    
    public void setPriority(final String priority) {
    
    
        this.priority = priority;
    }
    
    
    public void setSeverity(final String severity) {
    
    
        this.severity = severity;
    }
    
    
    /**
     * Method setstatus.
     * 
     * @param Status
     *            String
     */
    public void setStatus(final String Status) {
    
    
        status = Status;
    }
    
}
