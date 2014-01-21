/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.bugzilla;

import org.komea.backend.plugins.bugzilla.api.IBugZillaServerConfiguration;
import org.komea.backend.plugins.bugzilla.api.IBugZillaServerProxy;

/**
 *
 * @author rgalerme
 */
public class BugZillaServerConfiguration implements IBugZillaServerConfiguration {

    private String address;
    private String login;
    private String mdp;

    private IBugZillaServerProxy serverController;

    public BugZillaServerConfiguration(String address, String login, String mdp) {
        this.address = address;
        this.login = login;
        this.mdp = mdp;
    }

    @Override
    public IBugZillaServerProxy openProxy() {
        if (this.serverController == null) {
            J2BugZillaServerProxy servProx = new J2BugZillaServerProxy(address, login, mdp);
            servProx.connexion();
            this.serverController = servProx;
        }
        return serverController;
    }

    private void init() {

    }

}
