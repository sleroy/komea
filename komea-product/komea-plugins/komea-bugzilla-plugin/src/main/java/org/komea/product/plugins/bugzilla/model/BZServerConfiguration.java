/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.bugzilla.model;



import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.komea.product.backend.utils.StringList;
import org.komea.product.database.api.IHasId;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.utils.Validate;



/**
 * This class defines the server configuration.
 *
 * @author rgalerme
 * @version $Revision: 1.0 $
 */
public class BZServerConfiguration implements Serializable, IHasId
{
    
    
    /**
     * This field describes
     */
    private static final long     serialVersionUID   = 2673081036274879834L;
    
    private String                address            = null;
    
    private boolean               autocreateProjects = true;
    
    private StringList            closedStatus       = StringList.EMPTY;
    
    private StringList            fixedStatus        = new StringList("closed,delivered, resolved");
    
    private String                handler_field      = "assigned_to";
    private Integer               id;
    
    
    private String                login;
    
    
    private StringList            notfixedStatus     = StringList.EMPTY;
    
    
    private StringList            openedStatus       =
                                                             new StringList(
                                                                     "new,unconfirmed, onhold, accepted, assigned, opened, reopened");
    private String                password;
    private Integer               reminderAlert;
    
    
    private String                reporter_field     = "creator";
    
    private Map<String, Severity> severityMap        = new HashMap<String, Severity>();
    
    
    
    public BZServerConfiguration() {
    
    
        super();
        severityMap.put("info", Severity.INFO);
        severityMap.put("minor", Severity.MINOR);
        severityMap.put("major", Severity.MAJOR);
        severityMap.put("critical", Severity.CRITICAL);
        severityMap.put("blocker", Severity.BLOCKER);
        
        
    }
    
    
    /**
     * Method getAddress.
     *
     * @return String
     */
    public String getAddress() {
    
    
        return address;
    }
    
    
    /**
     * Returns the value of the field closedStatus.
     *
     * @return the closedStatus
     */
    public StringList getClosedStatus() {
    
    
        return closedStatus;
    }
    
    
    /**
     * Returns the value of the field fixedStatus.
     *
     * @return the fixedStatus
     */
    public StringList getFixedStatus() {
    
    
        return fixedStatus;
    }
    
    
    public String getHandler_field() {
    
    
        return handler_field;
    }
    
    
    /**
     * @return the id
     */
    @Override
    public Integer getId() {
    
    
        return id;
    }
    
    
    /**
     * Method getLogin.
     *
     * @return String
     */
    public String getLogin() {
    
    
        return login;
    }
    
    
    public StringList getNotfixedStatus() {
    
    
        return notfixedStatus;
    }
    
    
    public StringList getOpenedStatus() {
    
    
        return openedStatus;
    }
    
    
    /**
     * Method getMdp.
     *
     * @return String
     */
    public String getPassword() {
    
    
        return password;
    }
    
    
    public Integer getReminderAlert() {
    
    
        return reminderAlert;
    }
    
    
    public String getReporter_field() {
    
    
        return reporter_field;
    }
    
    
    public Map<String, Severity> getSeverityMap() {
    
    
        return severityMap;
    }
    
    
    public boolean isAutocreateProjects() {
    
    
        return autocreateProjects;
    }
    
    
    public boolean isResolutionFixed(final String _status) {
    
    
        Validate.isTrue(fixedStatus.isEmpty() ^ notfixedStatus.isEmpty());
        
        if (!fixedStatus.isEmpty()) {
            return fixedStatus.contains(_status);
        } else {
            return !notfixedStatus.contains(_status);
        }
    }
    
    
    public boolean isResolutionNotFixed(final String _status) {
    
    
        return !isResolutionFixed(_status);
    }
    
    
    public boolean isStatusClosed(final String _status) {
    
    
        return !isStatusOpened(_status);
    }
    
    
    public boolean isStatusOpened(final String _status) {
    
    
        Validate.isTrue(openedStatus.isEmpty() ^ closedStatus.isEmpty());
        
        if (!openedStatus.isEmpty()) {
            return openedStatus.contains(_status);
        } else {
            return !closedStatus.contains(_status);
        }
    }
    
    
    /**
     * Method setAddress.
     *
     * @param address
     *            String
     */
    public void setAddress(final String address) {
    
    
        this.address = address;
    }
    
    
    public void setAutocreateProjects(final boolean _autocreateProjects) {
    
    
        autocreateProjects = _autocreateProjects;
    }
    
    
    /**
     * Sets the field closedStatus with the value of _closedStatus.
     *
     * @param _closedStatus
     *            the closedStatus to set
     */
    public void setClosedStatus(final StringList _closedStatus) {
    
    
        closedStatus = _closedStatus;
    }
    
    
    /**
     * Sets the field fixedStatus with the value of _fixedStatus.
     *
     * @param _fixedStatus
     *            the fixedStatus to set
     */
    public void setFixedStatus(final StringList _fixedStatus) {
    
    
        fixedStatus = _fixedStatus;
    }
    
    
    /**
     * Sets the field handler_field with the value of _handler_field.
     *
     * @param _handler_field
     *            the handler_field to set
     */
    public void setHandler_field(final String _handler_field) {
    
    
        handler_field = _handler_field;
    }
    
    
    /**
     * @param _id
     *            the id to set
     */
    @Override
    public void setId(final Integer _id) {
    
    
        id = _id;
    }
    
    
    /**
     * Method setLogin.
     *
     * @param login
     *            String
     */
    public void setLogin(final String login) {
    
    
        this.login = login;
    }
    
    
    /**
     * Sets the field notfixedStatus with the value of _notfixedStatus.
     *
     * @param _notfixedStatus
     *            the notfixedStatus to set
     */
    public void setNotfixedStatus(final StringList _notfixedStatus) {
    
    
        notfixedStatus = _notfixedStatus;
    }
    
    
    /**
     * @param _openedStatus
     * @param _closedStatus
     */
    public void setOpenClosedStatus(final StringList _openedStatus, final StringList _closedStatus) {
    
    
        openedStatus = _openedStatus;
        closedStatus = _closedStatus;
        Validate.isTrue(openedStatus.isEmpty() ^ closedStatus.isEmpty());
        
    }
    
    
    /**
     * Sets the field openedStatus with the value of _openedStatus.
     *
     * @param _openedStatus
     *            the openedStatus to set
     */
    public void setOpenedStatus(final StringList _openedStatus) {
    
    
        openedStatus = _openedStatus;
    }
    
    
    /**
     * Method setMdp.
     *
     * @param password
     *            String
     */
    public void setPassword(final String mdp) {
    
    
        password = mdp;
    }
    
    
    public void setReminderAlert(final Integer reminderAlert) {
    
    
        this.reminderAlert = reminderAlert;
    }
    
    
    public void setReporter_field(final String _reporter_field) {
    
    
        reporter_field = _reporter_field;
    }
    
    
    /**
     * @param _empty
     * @param _stringList
     */
    public void setResolutions(final StringList _notFixed, final StringList _fixed) {
    
    
        fixedStatus = _fixed;
        notfixedStatus = _notFixed;
        Validate.isTrue(fixedStatus.isEmpty() ^ notfixedStatus.isEmpty());
        
    }
    
    
    /**
     * Sets the field severityMap with the value of _severityMap.
     *
     * @param _severityMap
     *            the severityMap to set
     */
    public void setSeverityMap(final Map<String, Severity> _severityMap) {
    
    
        severityMap = _severityMap;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "BZServerConfiguration [\\n\\taddress="
                + address + ", \\n\\tautocreateProjects=" + autocreateProjects
                + ", \\n\\tclosedStatus=" + closedStatus + ", \\n\\tfixedStatus=" + fixedStatus
                + ", \\n\\tid=" + id + ", \\n\\tlogin=" + login + ", \\n\\tnotfixedStatus="
                + notfixedStatus + ", \\n\\topenedStatus=" + openedStatus + ", \\n\\tpassword="
                + password + ", \\n\\treminderAlert=" + reminderAlert + "]";
    }
}
