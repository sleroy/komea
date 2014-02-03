/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.bugzilla;

import org.komea.backend.plugins.bugzilla.api.IBugZillaServerProxyFactory;
import org.komea.backend.plugins.bugzilla.api.IBugZillaServerConfiguration;
import org.komea.backend.plugins.bugzilla.api.IBugZillaServerProxy;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author rgalerme
 */
public class BugZillaServerConfiguration implements IBugZillaServerConfiguration {

    private final BugZillaServer server;

    private IBugZillaServerProxy serverController;

    private final IBugZillaServerProxyFactory serverProxyFactory;

    public BugZillaServerConfiguration(BugZillaServer server, IBugZillaServerProxyFactory serverProxyFactory) {

        this.server = server;
        this.serverProxyFactory = serverProxyFactory;
    }

    @Override
    public IBugZillaServerProxy openProxy() {
        if (this.serverController == null) {
            return serverProxyFactory.newConnector(this.server);
        }
        return serverController;
    }

    @Override
    public BugZillaContext getBugZillaContext() {
        return this.server.getContext();
    }



}
