/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.mantis.api;

import org.komea.product.plugins.mantis.model.MantisServerConfiguration;

/**
 *
 * @author rgalerme
 */
public interface IMantisServerProxyFactory {

    public IMantisServerProxy newConnector(MantisServerConfiguration serv);

    public IMantisServerProxy newTestConnector(MantisServerConfiguration serv);

}
