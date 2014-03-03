/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.testlink;

import org.komea.backend.plugins.testlink.api.ITestLinkServerConfiguration;
import org.komea.backend.plugins.testlink.api.ITestLinkServerProxy;
import org.komea.backend.plugins.testlink.api.ITestLinkServerProxyFactory;

/**
 *
 * @author rgalerme
 */
public class TestLinkServerConfiguration implements ITestLinkServerConfiguration {

    private final TestLinkServer testLinkServer;
    private final ITestLinkServerProxyFactory serverProxyFactory;
    private ITestLinkServerProxy proxy;

    TestLinkServerConfiguration(TestLinkServer testLinkServer, ITestLinkServerProxyFactory serverProxyFactory) {
        this.testLinkServer = testLinkServer;
        this.serverProxyFactory = serverProxyFactory;
    }

    @Override
    public ITestLinkServerProxy openProxy() {
        if (proxy == null) {
            this.proxy = serverProxyFactory.newConnector(testLinkServer);
        }
        return proxy;
    }

}
