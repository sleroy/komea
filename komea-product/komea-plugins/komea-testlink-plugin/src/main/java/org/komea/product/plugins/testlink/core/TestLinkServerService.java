/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.testlink.core;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.Validate;
import org.komea.product.backend.business.IDAOObjectStorage;
import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.backend.utils.SearchFilter;
import org.komea.product.plugins.testlink.api.ITestLinkServerDAO;
import org.komea.product.plugins.testlink.api.ITestLinkServerProxy;
import org.komea.product.plugins.testlink.api.ITestLinkServerProxyFactory;
import org.komea.product.plugins.testlink.model.TestLinkServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author rgalerme
 */
@Service
public class TestLinkServerService implements ITestLinkServerDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestLinkServerService.class);

    private IDAOObjectStorage<TestLinkServer> daoStorage;

    @Autowired
    private IPluginStorageService pluginStorageService;

    @Autowired
    private ITestLinkServerProxyFactory factory;

    @Override
    public boolean delete(final TestLinkServer _server) {

        return daoStorage.delete(_server);
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.testlink.api.ITestLinkServerDAO#find(java.lang.String)
     */
    @Override
    public TestLinkServer find(final String _serverName) {

        Validate.notNull(_serverName);
        return CollectionUtil.singleOrNull(daoStorage.find(new SearchFilter<TestLinkServer>() {

            @Override
            public boolean match(final TestLinkServer _object) {

                return _serverName.equals(_object.getName());
            }
        }));

    }

    /**
     * Returns the configuration storage.
     *
     * @return the configuration storage
     */
    public IDAOObjectStorage<TestLinkServer> getDaoStorage() {

        return daoStorage;
    }

    public IPluginStorageService getPluginStorageService() {

        return pluginStorageService;
    }

    @PostConstruct
    public void init() {

        LOGGER.debug("Initialisation of testlink plugin storage.");
        daoStorage = pluginStorageService.registerDAOStorage("TESTLINK", TestLinkServer.class);

    }

    @Override
    public void saveOrUpdate(final TestLinkServer server) {

        Validate.notNull(server);
        daoStorage.saveOrUpdate(server);
    }

    @Override
    public List<TestLinkServer> selectAll() {

        return daoStorage.selectAll();

    }

    public void setDaoStorage(final IDAOObjectStorage<TestLinkServer> _configurationStorage) {

        daoStorage = _configurationStorage;
    }

    public void setPluginStorageService(final IPluginStorageService _pluginStorageService) {

        pluginStorageService = _pluginStorageService;
    }

    @Override
    public boolean testConnexion(final TestLinkServer server) {
        ITestLinkServerProxy newConnector = null;
        try {
         newConnector = factory.newConnector(server);
        }catch(Exception e)
        {
        return false;
        }
        return newConnector != null;
    }

}
