/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.bugzilla;

import org.komea.backend.plugins.bugzilla.api.IBugZillaConfigurationService;
import org.komea.backend.plugins.bugzilla.data.BugZillaServer;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import org.komea.backend.plugins.bugzilla.BugZillaConfiguration;
import org.komea.backend.plugins.bugzilla.BugZillaServerConfiguration;

import org.komea.backend.plugins.bugzilla.api.IBugZillaServerConfiguration;
import org.komea.backend.plugins.bugzilla.api.IBugZillaServerProxyFactory;
import org.komea.backend.plugins.bugzilla.data.BugZillaServer;
import org.komea.product.backend.service.fs.IObjectStorage;
import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author rgalerme
 * @version $Revision: 1.0 $
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

    /**
     * Method getServers.
     *
     * @return List<IBugZillaServerConfiguration>
     * @see
     * org.komea.backend.plugins.bugzilla.api.IBugZillaConfigurationService#getServers()
     */
    @Override
    public List<IBugZillaServerConfiguration> getServers() {
        List<BugZillaServer> configurations = selectAll();
        List<IBugZillaServerConfiguration> result = new ArrayList<IBugZillaServerConfiguration>();
        for (BugZillaServer bugZillaServer : configurations) {
            result.add(new BugZillaServerConfiguration(bugZillaServer, serverProxyFactory));
        }

        return result;
    }

    @Override
    public void saveOrUpdate(BugZillaServer server, String oldAddress) {
        BugZillaConfiguration var = configurationStorage.get();
        List<BugZillaServer> configurations = var.getConfigurations();
        boolean find = false;
        for (BugZillaServer bugZillaServer : configurations) {
            if (bugZillaServer.getAddress().equals(oldAddress)) {
                bugZillaServer.setAddress(server.getAddress());
                bugZillaServer.setContext((server.getContext()));
                bugZillaServer.setLogin(server.getLogin());
                bugZillaServer.setMdp(server.getMdp());
                bugZillaServer.setReminderAlert(server.getReminderAlert());
                find = true;
                break;
            }

        }
        if (!find) {
            configurations.add(server);
        }
        configurationStorage.set(var);
    }

    @Override
    public List<BugZillaServer> selectAll() {
        BugZillaConfiguration var = configurationStorage.get();
        List<BugZillaServer> configurations = var.getConfigurations();
        configurationStorage.set(var);
        return configurations;
    }

    /**
     * Method getServerProxyFactory.
     *
     * @return IBugZillaServerProxyFactory
     * @see
     * org.komea.backend.plugins.bugzilla.api.IBugZillaConfigurationService#getServerProxyFactory()
     */
    @Override
    public IBugZillaServerProxyFactory getServerProxyFactory() {
        return serverProxyFactory;
    }

    /**
     * Method setServerProxyFactory.
     *
     * @param serverProxyFactory IBugZillaServerProxyFactory
     * @see
     * org.komea.backend.plugins.bugzilla.api.IBugZillaConfigurationService#setServerProxyFactory(IBugZillaServerProxyFactory)
     */
    @Override
    public void setServerProxyFactory(IBugZillaServerProxyFactory serverProxyFactory) {
        this.serverProxyFactory = serverProxyFactory;
    }

    /**
     * Method getPluginStorage.
     *
     * @return IPluginStorageService
     * @see
     * org.komea.backend.plugins.bugzilla.api.IBugZillaConfigurationService#getPluginStorage()
     */
    @Override
    public IPluginStorageService getPluginStorage() {
        return pluginStorage;
    }

    /**
     * Method setPluginStorage.
     *
     * @param pluginStorage IPluginStorageService
     * @see
     * org.komea.backend.plugins.bugzilla.api.IBugZillaConfigurationService#setPluginStorage(IPluginStorageService)
     */
    @Override
    public void setPluginStorage(IPluginStorageService pluginStorage) {
        this.pluginStorage = pluginStorage;
    }

}
