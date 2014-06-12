/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.bugzilla.api;

import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.ConnectionException;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;

/**
 *
 * @author rgalerme
 */
public interface IBZServerProxyFactory {

    public IBZServerProxy newConnector(BZServerConfiguration serv);

    public IBZServerProxy newTestConnector(BZServerConfiguration serv) throws ConnectionException, BugzillaException;

}
