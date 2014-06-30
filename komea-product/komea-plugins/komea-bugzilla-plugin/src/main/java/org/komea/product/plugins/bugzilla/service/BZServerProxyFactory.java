/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.bugzilla.service;



import org.komea.product.plugins.bugzilla.api.IBZServerProxy;
import org.komea.product.plugins.bugzilla.api.IBZServerProxyFactory;
import org.komea.product.plugins.bugzilla.core.BZServerProxy;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.springframework.stereotype.Service;

import com.j2bugzilla.base.BugzillaConnector;
import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.ConnectionException;
import com.j2bugzilla.rpc.LogIn;



/**
 * @author rgalerme
 * @version $Revision: 1.0 $
 */
@Service
public class BZServerProxyFactory implements IBZServerProxyFactory
{
    
    
    /**
     * Method connexion.
     *
     * @param _server
     *            BZServerConfiguration
     * @return BugzillaConnector
     */
    public BugzillaConnector connexion(final BZServerConfiguration _server) {
    
    
        BugzillaConnector conn = null;
        try {
            conn = new BugzillaConnector();
            conn.connectTo(_server.getAddress());
            final LogIn logIn = new LogIn(_server.getLogin(), _server.getPassword());
            conn.executeMethod(logIn);
        } catch (final Exception ex) {
            throw new BugzillaPluginException(ex);
        }
        return conn;
    }
    
    
    /**
     * Method newConnector.
     *
     * @param serv
     *            BZServerConfiguration
     * @return IBZServerProxy
     * @see org.komea.backend.IBZServerProxyFactory.bugzilla.api.IBugZillaServerProxyFactory#newConnector(BZServerConfiguration)
     */
    @Override
    public IBZServerProxy newConnector(final BZServerConfiguration serv) {
    
    
        return new BZServerProxy(connexion(serv));
    }
    
    
    @Override
    public IBZServerProxy newTestConnector(final BZServerConfiguration serv)
            throws ConnectionException, BugzillaException {
    
    
        return new BZServerProxy(testConnexion(serv));
    }
    
    
    private BugzillaConnector testConnexion(final BZServerConfiguration _server)
            throws ConnectionException, BugzillaException {
    
    
        BugzillaConnector conn = null;
        conn = new BugzillaConnector();
        conn.connectTo(_server.getAddress());

        final LogIn logIn = new LogIn(_server.getLogin(), _server.getPassword());
        conn.executeMethod(logIn);
        return conn;
    }
    
}
