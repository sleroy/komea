/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.testlink.api;



import org.komea.product.plugins.testlink.model.TestLinkServer;



/**
 * This interface defines the factory to build a testlink proxy.
 * 
 * @author rgalerme
 */
public interface ITestLinkServerProxyFactory
{
    
    
    public ITestLinkServerProxy newConnector(TestLinkServer testLinkServer);
    
}
