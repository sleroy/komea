/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.bugzilla.model;



import java.io.Serializable;
import java.net.URL;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.komea.product.database.api.IHasId;
import org.komea.product.plugins.bugzilla.api.BugStatusGroup;

import com.google.common.collect.Lists;



/**
 * This class defines the server configuration.
 * 
 * @author rgalerme
 * @version $Revision: 1.0 $
 */
public class BZServerConfiguration implements Serializable, IHasId
{
    
    
    private URL                                     address      = null;
    
    
    private Integer                                 id;
    
    
    private String                                  login;
    
    
    private String                                  password;
    
    
    private final List<String>                      priorities   = Lists.newArrayList();
    private Integer                                 reminderAlert;
    private final List<String>                      severities   = Lists.newArrayList();
    private final Map<BugStatusGroup, List<String>> statusGroups =
                                                                         new EnumMap<BugStatusGroup, List<String>>(
                                                                                 BugStatusGroup.class);
    private final List<String>                      statutes     = Lists.newArrayList();
    
    
    
    public BZServerConfiguration() {
    
    
    }
    
    
    public BZServerConfiguration(final URL address, final String login, final String mdp) {
    
    
        this.address = address;
        this.login = login;
        password = mdp;
    }
    
    
    public BZServerConfiguration(
            final URL address,
            final String login,
            final String mdp,
            final int _reminder) {
    
    
        this.address = address;
        this.login = login;
        password = mdp;
        reminderAlert = _reminder;
    }
    
    
    public BZServerConfiguration(
            final URL address,
            final String login,
            final String mdp,
            final int _reminder,
            final List<String> statutes,
            final List<String> severities,
            final List<String> priorities,
            final Map<BugStatusGroup, List<String>> statusGroups) {
    
    
        this.address = address;
        this.login = login;
        password = mdp;
        reminderAlert = _reminder;
        this.statutes.addAll(statutes);
        this.severities.addAll(severities);
        this.priorities.addAll(priorities);
        this.statusGroups.putAll(statusGroups);
    }
    
    
    /**
     * Method getAddress.
     * 
     * @return String
     */
    public URL getAddress() {
    
    
        return address;
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
    
    
    /**
     * Method getMdp.
     * 
     * @return String
     */
    public String getPassword() {
    
    
        return password;
    }
    
    
    public List<String> getPriorities() {
    
    
        return priorities;
    }
    
    
    public Integer getReminderAlert() {
    
    
        return reminderAlert;
    }
    
    
    public List<String> getSeverities() {
    
    
        return severities;
    }
    
    
    public Map<BugStatusGroup, List<String>> getStatusGroups() {
    
    
        return statusGroups;
    }
    
    
    public List<String> getStatutes() {
    
    
        return statutes;
    }
    
    
    /**
     * Method setAddress.
     * 
     * @param address
     *            String
     */
    public void setAddress(final URL address) {
    
    
        this.address = address;
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
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "BZServerConfiguration [address="
                + address + ", login=" + login + ", password=" + password + ", priorities="
                + priorities + ", reminderAlert=" + reminderAlert + ", severities=" + severities
                + ", statusGroups=" + statusGroups + ", statutes=" + statutes + "]";
    }
}
