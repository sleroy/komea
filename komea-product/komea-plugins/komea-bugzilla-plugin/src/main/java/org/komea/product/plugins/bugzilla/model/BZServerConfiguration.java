/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.bugzilla.model;



import java.io.Serializable;

import org.komea.product.backend.utils.StringList;
import org.komea.product.database.api.IHasId;
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
    private static final long serialVersionUID   = 2673081036274879834L;

    private String            address            = null;

    private boolean           autocreateProjects = true;

    private final StringList  closedStatus       = StringList.EMPTY;

    private final StringList  fixedStatus        = new StringList("closed,delivered, resolved");

    private Integer           id;
    private String            login;
    private final StringList  notfixedStatus     = StringList.EMPTY;
    private final StringList  openedStatus       =
            new StringList(
                    "new,unconfirmed, onhold, accepted, assigned, opened, reopened");
    private String            password;


    private Integer           reminderAlert;



    public BZServerConfiguration() {


        super();


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


    public Integer getReminderAlert() {


        return reminderAlert;
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
            return closedStatus.contains(_status);
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


        return "BZServerConfiguration [\\n\\taddress="
                + address + ", \\n\\tautocreateProjects=" + autocreateProjects
                + ", \\n\\tclosedStatus=" + closedStatus + ", \\n\\tfixedStatus=" + fixedStatus
                + ", \\n\\tid=" + id + ", \\n\\tlogin=" + login + ", \\n\\tnotfixedStatus="
                + notfixedStatus + ", \\n\\topenedStatus=" + openedStatus + ", \\n\\tpassword="
                + password + ", \\n\\treminderAlert=" + reminderAlert + "]";
    }
}
