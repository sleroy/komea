/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.bugzilla.model;



import java.io.Serializable;
import java.util.HashMap;
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
    
    
    /**
     * This field describes
     */
    private static final long                       serialVersionUID   = 2673081036274879834L;
    
    private String                                  address            = null;
    
    private boolean                                 autocreateProjects = true;
    
    private Integer                                 id;
    
    private String                                  login;
    
    private String                                  password;
    private final List<String>                      priorities         = Lists.newArrayList();
    private final Map<String, String>               projectAliases     =
                                                                               new HashMap<String, String>();
    private Integer                                 reminderAlert;
    private final List<String>                      severities         = Lists.newArrayList();
    
    private final List<String>                      status             = Lists.newArrayList();
    
    private final Map<BugStatusGroup, List<String>> statusGroups;
    
    
    
    public BZServerConfiguration() {
    
    
        super();
        statusGroups = new HashMap<BugStatusGroup, List<String>>();
        
        // statusGroups.put(BugStatusGroup.OPEN, arg1);
        // statusGroups.put(BugStatusGroup.CLOSED, arg1);
        // statusGroups.put(BugStatusGroup.OPEN_NOT_FIXED, arg1);
        
    }
    
    
    /**
     * Returns the alias or creates it with the name of the project name.
     * 
     * @param _projectName
     *            the project name
     * @return
     */
    public String createOrRetrieveAliasForProjectName(final String _projectName) {
    
    
        String possibleAlias = projectAliases.get(_projectName);
        if (possibleAlias == null) {
            possibleAlias = _projectName;
            projectAliases.put(_projectName, _projectName);
        }
        return possibleAlias;
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
    
    
    public List<String> getStatus() {
    
    
        return status;
    }
    
    
    public Map<BugStatusGroup, List<String>> getStatusGroups() {
    
    
        return statusGroups;
    }
    
    
    public boolean isAutocreateProjects() {
    
    
        return autocreateProjects;
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
                + ", statusGroups=" + statusGroups + ", status=" + status + "]";
    }
}
