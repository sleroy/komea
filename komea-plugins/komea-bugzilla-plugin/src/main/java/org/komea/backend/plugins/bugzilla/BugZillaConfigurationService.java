/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.bugzilla;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.komea.backend.plugins.bugzilla.api.IBugZillaConfigurationService;
import org.komea.backend.plugins.bugzilla.api.IBugZillaServerConfiguration;
import org.komea.backend.plugins.bugzilla.api.IBugZillaServerProxyFactory;
import org.komea.product.backend.fs.IObjectStorage;
import org.komea.product.backend.service.IPluginStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author rgalerme
 */
@Service
public class BugZillaConfigurationService implements IBugZillaConfigurationService {

    @Autowired
    private IPluginStorageService pluginStorage;

    @Autowired
    private IBugZillaServerProxyFactory serverProxyFactory;

    private IObjectStorage<BugZillaConfiguration> configurationStorage;

    @PostConstruct
    public void init() {
        configurationStorage = pluginStorage.registerStorage("BUGZILLA", BugZillaConfiguration.class);
    }

    @Override
    public List<IBugZillaServerConfiguration> getServers() {
        BugZillaConfiguration var = configurationStorage.get();
        List<BugZillaServer> configurations = var.getConfigurations();
        configurationStorage.set(var);
        List<IBugZillaServerConfiguration> result = new ArrayList<IBugZillaServerConfiguration>();
        for (BugZillaServer bugZillaServer : configurations) {
            result.add(new BugZillaServerConfiguration(bugZillaServer, serverProxyFactory));
        }

        return result;
    }

    @Override
    public IBugZillaServerProxyFactory getServerProxyFactory() {
        return serverProxyFactory;
    }

    @Override
    public void setServerProxyFactory(IBugZillaServerProxyFactory serverProxyFactory) {
        this.serverProxyFactory = serverProxyFactory;
    }

    @Override
    public IPluginStorageService getPluginStorage() {
        return pluginStorage;
    }

    @Override
    public void setPluginStorage(IPluginStorageService pluginStorage) {
        this.pluginStorage = pluginStorage;
    }
}
