/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.testlink;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.komea.backend.plugins.testlink.api.ITestLinkServerConfiguration;
import org.komea.backend.plugins.testlink.api.ITestLinkServerManagerService;
import org.komea.backend.plugins.testlink.api.ITestLinkServerProxyFactory;
import org.komea.product.backend.fs.IObjectStorage;
import org.komea.product.backend.service.IPluginStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author rgalerme
 */
@Service
public class TestLinkServerManagerService implements ITestLinkServerManagerService {

    @Autowired
    private IPluginStorageService pluginStorage;

    @Autowired
    private ITestLinkServerProxyFactory serverProxyFactory;

    private IObjectStorage<TestLinkStorageConfiguration> configurationStorage;

    @PostConstruct
    public void init() {
        configurationStorage = pluginStorage.registerStorage("TESTLINK", TestLinkStorageConfiguration.class);
    }

    @Override
    public List<ITestLinkServerConfiguration> getServers() {
        TestLinkStorageConfiguration var = configurationStorage.get();
        List<TestLinkServer> configurations = var.getConfigurations();
        configurationStorage.set(var);
        List<ITestLinkServerConfiguration>  result = new ArrayList<ITestLinkServerConfiguration>();
        for (TestLinkServer testLinkServer : configurations) {
            result.add(new TestLinkServerConfiguration(testLinkServer,serverProxyFactory));
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

}
