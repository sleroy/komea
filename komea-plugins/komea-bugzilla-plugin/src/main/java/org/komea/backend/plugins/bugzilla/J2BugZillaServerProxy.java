/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.bugzilla;

import com.j2bugzilla.base.BugzillaConnector;
import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.ConnectionException;
import com.j2bugzilla.rpc.LogIn;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.komea.backend.plugins.bugzilla.api.IBugZillaServerProxy;

/**
 *
 * @author rgalerme
 */
public class J2BugZillaServerProxy implements IBugZillaServerProxy {

    private BugzillaConnector conn;
    private String address;
    private String login;
    private String mdp;

    public J2BugZillaServerProxy(String address, String login, String mdp) {
        this.address = address;
        this.login = login;
        this.mdp = mdp;
    }

    @Override
    public List<String> getListProjects() {
        return null;
    }

    @Override
    public List<BugzillaBug> getListBugs(String Project) {
        return null;
    }

    @Override
    public List<BugzillaBug> getListBugs(String Project, BugZillaStatus... status) {
        return null;
    }

    void connexion() {
        try {
            conn = new BugzillaConnector();
            conn.connectTo(address);
            LogIn logIn = new LogIn(login, mdp);
            try {
                conn.executeMethod(logIn);
            } catch (BugzillaException ex) {
                Logger.getLogger(BugZillaServerConfiguration.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ConnectionException ex) {
            Logger.getLogger(BugZillaServerConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
