/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.bugzilla.data;

import java.io.Serializable;

/**
 *
 * @author rgalerme
 * @version $Revision: 1.0 $
 */
public class BugZillaServer implements Serializable {

    private String address;
    private String login;
    private String mdp;
    private BugZillaContext context;
    private Integer reminderAlert;

    public BugZillaServer() {
    }

    /**
     * Constructor for BugZillaServer.
     * @param address String
     * @param login String
     * @param mdp String
     * @param context BugZillaContext
     */
    public BugZillaServer(String address, String login, String mdp, BugZillaContext context) {
        this.address = address;
        this.login = login;
        this.mdp = mdp;
        this.context = context;
    }
    
     public BugZillaServer(String address, String login, String mdp, BugZillaContext context,int reminder) {
        this.address = address;
        this.login = login;
        this.mdp = mdp;
        this.context = context;
        this.reminderAlert = reminder;
    }

    public Integer getReminderAlert() {
        return reminderAlert;
    }

    public void setReminderAlert(Integer reminderAlert) {
        this.reminderAlert = reminderAlert;
    }

     
     
    /**
     * Method setAddress.
     * @param address String
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Method setLogin.
     * @param login String
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Method setMdp.
     * @param mdp String
     */
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    /**
     * Method setContext.
     * @param context BugZillaContext
     */
    public void setContext(BugZillaContext context) {
        this.context = context;
    }

    /**
     * Method getAddress.
     * @return String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Method getLogin.
     * @return String
     */
    public String getLogin() {
        return login;
    }

    /**
     * Method getMdp.
     * @return String
     */
    public String getMdp() {
        return mdp;
    }

    /**
     * Method getContext.
     * @return BugZillaContext
     */
    public BugZillaContext getContext() {
        return context;
    }

}
