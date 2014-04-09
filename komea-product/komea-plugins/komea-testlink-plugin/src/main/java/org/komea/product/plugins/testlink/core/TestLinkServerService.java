/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.testlink.core;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.komea.product.plugins.testlink.api.ITestLinkServerConfiguration;
import org.komea.product.plugins.testlink.api.ITestLinkServerService;
import org.komea.product.plugins.testlink.api.ITestLinkServerProxyFactory;
import org.komea.product.backend.service.fs.IObjectStorage;
import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author rgalerme
 */
@Service
public class TestLinkServerService implements ITestLinkServerService {

    @Autowired
    private IPluginStorageService pluginStorage;

    @Autowired
    private ITestLinkServerProxyFactory serverProxyFactory;

    private IObjectStorage<TestLinkStorageConfiguration> configurationStorage;

    @PostConstruct
    public void init() {
        configurationStorage = pluginStorage.registerStorage("TESTLINK", TestLinkStorageConfiguration.class);
        TestLinkStorageConfiguration var = configurationStorage.get();
        if (var == null) {
            var = new TestLinkStorageConfiguration();
            configurationStorage.set(var);
        }
//        TestLinkServer testLinkServer = new TestLinkServer("http://ares.tocea/testlink/lib/api/xmlrpc.php",
//                "2dec70df08045278463817fb15d79c4d");

    }

    @Override
    public List<ITestLinkServerConfiguration> getServers() {
        TestLinkStorageConfiguration var = configurationStorage.get();
        List<TestLinkServer> configurations = var.getConfigurations();
        List<ITestLinkServerConfiguration> result = new ArrayList<ITestLinkServerConfiguration>();
        for (TestLinkServer testLinkServer : configurations) {
            result.add(new TestLinkServerConfiguration(testLinkServer, serverProxyFactory));
        }
        return result;
    }

    @Override
    public IPluginStorageService getPluginStorage() {
        return this.pluginStorage;
    }

    @Override
    public void setPluginStorage(IPluginStorageService pluginStorage) {
        this.pluginStorage = pluginStorage;
    }

    @Override
    public ITestLinkServerProxyFactory getServerProxyFactory() {
        return this.serverProxyFactory;
    }

    @Override
    public void setServerProxyFactory(ITestLinkServerProxyFactory serverProxyFactory) {
        this.serverProxyFactory = serverProxyFactory;
    }

    public IObjectStorage<TestLinkStorageConfiguration> getConfigurationStorage() {
        return configurationStorage;
    }

    public void setConfigurationStorage(IObjectStorage<TestLinkStorageConfiguration> configurationStorage) {
        this.configurationStorage = configurationStorage;
    }

    @Override
    public void saveOrUpdate(TestLinkServer server, String oldAddress) {
        TestLinkStorageConfiguration var = configurationStorage.get();
        List<TestLinkServer> selectAll = var.getConfigurations();
        boolean find = false;
        for (TestLinkServer testLinkServer : selectAll) {
            if (testLinkServer.getAddress().equals(oldAddress)) {
                testLinkServer.setAddress(server.getAddress());
                testLinkServer.setKey(server.getKey());
                find = true;
                break;
            }
        }
        if (!find) {
            selectAll.add(server);
        }
        configurationStorage.set(var);
    }

    @Override
    public List<TestLinkServer> selectAll() {
        TestLinkStorageConfiguration var = configurationStorage.get();
        return var.getConfigurations();

    }

    @Override
    public boolean delete(TestLinkServer _server) {
        TestLinkStorageConfiguration var = configurationStorage.get();
        List<TestLinkServer> selectAll = var.getConfigurations();
        for (TestLinkServer testlinkServer : selectAll) {
            if (testlinkServer.getAddress().equals(_server.getAddress())
                    && testlinkServer.getKey().equals(_server.getKey())) {
                selectAll.remove(testlinkServer);
                configurationStorage.set(var);
                return true;
            }
        }
        return false;
    }

}
