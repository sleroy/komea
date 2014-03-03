/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.bugzilla.api;

import java.util.List;

import org.komea.product.backend.service.plugins.IPluginStorageService;

/**
 *
 * @author rgalerme
 */
public interface IBugZillaConfigurationService {

    /**
     *
     * @return
     */
    public List<IBugZillaServerConfiguration> getServers();

    public IPluginStorageService getPluginStorage();

    public void setPluginStorage(IPluginStorageService pluginStorage);

    public IBugZillaServerProxyFactory getServerProxyFactory();

    public void setServerProxyFactory(IBugZillaServerProxyFactory serverProxyFactory);
}
