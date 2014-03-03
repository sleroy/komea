/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.testlink.api;

import java.util.List;

import org.komea.product.backend.service.plugins.IPluginStorageService;

/**
 *
 * @author rgalerme
 */
public interface ITestLinkServerManagerService {

    public List<ITestLinkServerConfiguration> getServers();

    public IPluginStorageService getPluginStorage();

    public void setPluginStorage(IPluginStorageService pluginStorage);

    public ITestLinkServerProxyFactory getServerProxyFactory();

    public void setServerProxyFactory(ITestLinkServerProxyFactory serverProxyFactory);
}
