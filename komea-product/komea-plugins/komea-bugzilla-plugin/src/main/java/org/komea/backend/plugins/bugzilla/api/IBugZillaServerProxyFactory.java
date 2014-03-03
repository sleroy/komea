/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.backend.plugins.bugzilla.api;

import org.komea.backend.plugins.bugzilla.BugZillaServer;

/**
 *
 * @author rgalerme
 */
public interface IBugZillaServerProxyFactory {

    public IBugZillaServerProxy newConnector(BugZillaServer serv);


}
