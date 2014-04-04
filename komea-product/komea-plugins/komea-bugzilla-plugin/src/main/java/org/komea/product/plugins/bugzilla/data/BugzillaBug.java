/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.bugzilla.data;



import java.io.Serializable;
import java.util.Date;



/**
 * @author rgalerme
 * @version $Revision: 1.0 $
 */
public class BugzillaBug implements Serializable
{
    
    
    private int     id;
    private String  Status;
    private boolean is_open;
    private Date    date_creation;
    private boolean is_assigned;
    
    
    
    public BugzillaBug() {
    
    
    }
    
    
    /**
     * Constructor for BugzillaBug.
     * @param id int
     * @param Status String
     * @param is_open boolean
     * @param date_creation Date
     * @param is_assigned boolean
     */
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
    
    
    /**
     * Method getDate_creation.
     * @return Date
     */
    public Date getDate_creation() {
    
    
        return date_creation;
    }
    
    
    /**
     * Method getId.
     * @return int
     */
    public int getId() {
    
    
        return id;
    }
    
    
    /**
     * Method getStatus.
     * @return String
     */
    public String getStatus() {
    
    
        return Status;
    }
    
    
    /**
     * Method isIs_assigned.
     * @return boolean
     */
    public boolean isIs_assigned() {
    
    
        return is_assigned;
    }
    
    
    /**
     * Method isIs_open.
     * @return boolean
     */
    public boolean isIs_open() {
    
    
        return is_open;
    }
    
    
    /**
     * Method setDate_creation.
     * @param date_creation Date
     */
    public void setDate_creation(final Date date_creation) {
    
    
        this.date_creation = date_creation;
    }
    
    
    /**
     * Method setId.
     * @param id int
     */
    public void setId(final int id) {
    
    
        this.id = id;
    }
    
    
    /**
     * Method setIs_assigned.
     * @param is_assigned boolean
     */
    public void setIs_assigned(final boolean is_assigned) {
    
    
        this.is_assigned = is_assigned;
    }
    
    
    /**
     * Method setIs_open.
     * @param is_open boolean
     */
    public void setIs_open(final boolean is_open) {
    
    
        this.is_open = is_open;
    }
    
    
    /**
     * Method setStatus.
     * @param Status String
     */
    public void setStatus(final String Status) {
    
    
        this.Status = Status;
    }
    
}
