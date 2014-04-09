/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.bugzilla.core;

import org.komea.product.plugins.bugzilla.data.BugZillaContext;
import org.komea.product.plugins.bugzilla.data.BugZillaServer;
import org.komea.product.plugins.bugzilla.api.IBugZillaServerProxyFactory;
import org.komea.product.plugins.bugzilla.api.IBugZillaServerConfiguration;
import org.komea.product.plugins.bugzilla.api.IBugZillaServerProxy;

/**
 *
 * @author rgalerme
 * @version $Revision: 1.0 $
 */
public class BugZillaServerConfiguration implements IBugZillaServerConfiguration {

    private final BugZillaServer server;

    private IBugZillaServerProxy serverController;

    private final IBugZillaServerProxyFactory serverProxyFactory;

    /**
     * Constructor for BugZillaServerConfiguration.
     *
     * @param server BugZillaServer
     * @param serverProxyFactory IBugZillaServerProxyFactory
     */
    public BugZillaServerConfiguration(BugZillaServer server, IBugZillaServerProxyFactory serverProxyFactory) {

        this.server = server;
        this.serverProxyFactory = serverProxyFactory;
    }

    /**
     * Method openProxy.
     *
     * @return IBugZillaServerProxy
     * @see
     * org.komea.backend.plugins.bugzilla.api.IBugZillaServerConfiguration#openProxy()
     */
    @Override
    public IBugZillaServerProxy openProxy() {
        if (this.serverController == null) {
            return serverProxyFactory.newConnector(this.server);
        }
        return serverController;
    }

    /**
     * Method getBugZillaContext.
     *
     * @return BugZillaContext
     * @see
     * org.komea.backend.plugins.bugzilla.api.IBugZillaServerConfiguration#getBugZillaContext()
     */
    @Override
    public BugZillaContext getBugZillaContext() {
        return this.server.getContext();
    }

    @Override
    public int getReminderAlert() {
        return this.server.getReminderAlert();
    }

    @Override
    public BugZillaServer getServer() {
        return server;
    }

}
