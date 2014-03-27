/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.bugzilla;

import org.komea.backend.plugins.bugzilla.data.BugZillaServer;
import com.j2bugzilla.base.BugzillaConnector;
import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.ConnectionException;
import com.j2bugzilla.rpc.LogIn;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.komea.backend.plugins.bugzilla.api.IBugZillaServerProxy;
import org.komea.backend.plugins.bugzilla.api.IBugZillaServerProxyFactory;

/**
 *
 * @author rgalerme
 * @version $Revision: 1.0 $
 */
public class BugZillaServerProxyFactory implements IBugZillaServerProxyFactory {

    /**
     * Method newConnector.
     * @param serv BugZillaServer
     * @return IBugZillaServerProxy
     * @see org.komea.backend.plugins.bugzilla.api.IBugZillaServerProxyFactory#newConnector(BugZillaServer)
     */
    @Override
    public IBugZillaServerProxy newConnector(BugZillaServer serv) {
        return new J2BugZillaServerProxy(connexion(serv));
    }

    /**
     * Method connexion.
     * @param serv BugZillaServer
     * @return BugzillaConnector
     */
    private BugzillaConnector connexion(BugZillaServer serv) {

        BugzillaConnector conn = null;
        try {
            conn = new BugzillaConnector();
            conn.connectTo(serv.getAddress());
            LogIn logIn = new LogIn(serv.getLogin(), serv.getMdp());
            try {
                conn.executeMethod(logIn);
            } catch (BugzillaException ex) {
                Logger.getLogger(BugZillaServerConfiguration.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ConnectionException ex) {
            Logger.getLogger(BugZillaServerConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    /*                J2BugZillaServerProxy servProx = new J2BugZillaServerProxy(bugzillaConnector);
     this.serverController = servProx;
     servProx.connexion();
    
    

    
    
     */
}
