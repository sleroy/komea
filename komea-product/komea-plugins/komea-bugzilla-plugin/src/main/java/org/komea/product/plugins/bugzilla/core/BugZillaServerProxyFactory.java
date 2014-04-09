/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.bugzilla.core;

import org.komea.product.plugins.bugzilla.data.BugZillaServer;
import com.j2bugzilla.base.BugzillaConnector;
import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.ConnectionException;
import com.j2bugzilla.rpc.LogIn;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.komea.product.plugins.bugzilla.api.IBugZillaServerProxy;
import org.komea.product.plugins.bugzilla.api.IBugZillaServerProxyFactory;
import org.komea.product.plugins.bugzilla.data.BugZillaServer;
import org.springframework.stereotype.Service;

/**
 *
 * @author rgalerme
 * @version $Revision: 1.0 $
 */
@Service
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
