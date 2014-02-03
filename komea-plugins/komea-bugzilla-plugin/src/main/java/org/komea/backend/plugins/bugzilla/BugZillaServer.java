/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.bugzilla;

/**
 *
 * @author rgalerme
 */
public class BugZillaServer {

    private String address;
    private String login;
    private String mdp;
    private BugZillaContext context;

    public BugZillaServer() {
    }

    public BugZillaServer(String address, String login, String mdp, BugZillaContext context) {
        this.address = address;
        this.login = login;
        this.mdp = mdp;
        this.context = context;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public void setContext(BugZillaContext context) {
        this.context = context;
    }

    public String getAddress() {
        return address;
    }

    public String getLogin() {
        return login;
    }

    public String getMdp() {
        return mdp;
    }

    public BugZillaContext getContext() {
        return context;
    }

}
