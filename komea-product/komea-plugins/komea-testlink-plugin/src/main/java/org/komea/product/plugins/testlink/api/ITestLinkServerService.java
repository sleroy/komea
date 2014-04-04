/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.testlink.api;

import java.util.List;

import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.komea.product.plugins.testlink.core.TestLinkServer;

/**
 *
 * @author rgalerme
 */
public interface ITestLinkServerService {

    public List<ITestLinkServerConfiguration> getServers();

    public IPluginStorageService getPluginStorage();

    public void setPluginStorage(IPluginStorageService pluginStorage);

    public ITestLinkServerProxyFactory getServerProxyFactory();

    public void setServerProxyFactory(ITestLinkServerProxyFactory serverProxyFactory);

    /**
     *
     * @param server
     * @return
     */
    public void saveOrUpdate(TestLinkServer server, String oldAddress);

    /**
     *
     * @return
     */
    public List<TestLinkServer> selectAll();

    /**
     *
     * @param server
     */
    public void delete(TestLinkServer server);
}
