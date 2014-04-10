/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.testlink.core;



import org.komea.product.plugins.testlink.api.ITestLinkServerProxy;
import org.komea.product.plugins.testlink.api.ITestLinkServerProxyFactory;
import org.komea.product.plugins.testlink.business.TestLinkJavaAPI;
import org.komea.product.plugins.testlink.model.TestLinkServer;
import org.springframework.stereotype.Service;



/**
 * @author rgalerme
 */
@Service
public class TestLinkServerProxyFactory implements ITestLinkServerProxyFactory
{
    
    
    @Override
    public ITestLinkServerProxy newConnector(final TestLinkServer testLinkServer) {
    
    
        final TestLinkJavaAPI apiConn = new TestLinkJavaAPI();
        apiConn.connexion(testLinkServer);
        return apiConn;
    }
    
}
