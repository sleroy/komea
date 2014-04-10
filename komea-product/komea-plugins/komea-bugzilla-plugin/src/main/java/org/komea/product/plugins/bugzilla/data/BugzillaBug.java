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
public class BugzillaBug implements Serializable {

    private int id;
    private String status;
    private boolean is_open;
    private Date date_creation;
    private boolean is_assigned;
    private String severity;
    private String priority;

    public BugzillaBug() {

    }

    /**
     * Constructor for BugzillaBug.
     *
     * @param id int
     * @param status String
     * @param is_open boolean
     * @param date_creation Date
     * @param is_assigned boolean
     * @param severity severity
     * @param priority priority
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
        this.is_open = is_open;
        this.date_creation = date_creation;
        this.is_assigned = is_assigned;
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
    public boolean isIs_assigned() {

        return is_assigned;
    }

    /**
     * Method isIs_open.
     *
     * @return boolean
     */
    public boolean isIs_open() {

        return is_open;
    }

    /**
     * Method setDate_creation.
     *
     * @param date_creation Date
     */
    public void setDate_creation(final Date date_creation) {

        this.date_creation = date_creation;
    }

    /**
     * Method setId.
     *
     * @param id int
     */
    public void setId(final int id) {

        this.id = id;
    }

    /**
     * Method setIs_assigned.
     *
     * @param is_assigned boolean
     */
    public void setIs_assigned(final boolean is_assigned) {

        this.is_assigned = is_assigned;
    }

    /**
     * Method setIs_open.
     *
     * @param is_open boolean
     */
    public void setIs_open(final boolean is_open) {

        this.is_open = is_open;
    }

    /**
     * Method setstatus.
     *
     * @param Status String
     */
    public void setStatus(final String Status) {

        this.status = Status;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

}
