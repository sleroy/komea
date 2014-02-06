/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.backend.plugins.testlink;

import org.komea.backend.plugins.testlink.api.ITestLinkServerProxy;
import org.komea.backend.plugins.testlink.api.ITestLinkServerProxyFactory;

/**
 *
 * @author rgalerme
 */
public class TestLinkServerProxyFactory implements ITestLinkServerProxyFactory {

    @Override
    public ITestLinkServerProxy newConnector(TestLinkServer testLinkServer) {
        TestLinkJavaAPI apiConn = new TestLinkJavaAPI();
        apiConn.connexion(testLinkServer);
        return apiConn;
    }
    
}
