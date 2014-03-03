/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.backend.plugins.testlink.api;

import org.komea.backend.plugins.testlink.TestLinkServer;

/**
 *
 * @author rgalerme
 */
public interface ITestLinkServerProxyFactory {

    public ITestLinkServerProxy newConnector(TestLinkServer testLinkServer);
    
}
