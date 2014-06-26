/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.bugzilla.service;

import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.ConnectionException;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.Validate;
import org.komea.product.backend.business.IDAOObjectStorage;
import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.backend.utils.SearchFilter;
import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.plugins.bugzilla.api.IBZServerProxy;
import org.komea.product.plugins.bugzilla.api.IBZServerProxyFactory;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author rgalerme
 * @version $Revision: 1.0 $
 */
@Service
public class BZConfigurationDAO implements IBZConfigurationDAO {

    /**
     * @author sleroy
     */
    private final class SearchFilterImplementation implements SearchFilter<BZServerConfiguration> {

        /**
         *
         */
        private final String string;

        /**
         * @param _string
         */
        private SearchFilterImplementation(final String _string) {

            string = _string;
        }

        @Override
        public boolean match(final BZServerConfiguration _object) {

            return string.equals(_object.getAddress());
        }
    }

    private IDAOObjectStorage<BZServerConfiguration> configurationStorage;

    @Autowired
    private IPluginStorageService pluginStorage;

    @Autowired
    private IBZServerProxyFactory serverProxyFactory;

    @Override
    public boolean delete(final BZServerConfiguration _object) {

        return configurationStorage.delete(_object);

    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO#find(java.lang.String)
     */
    @Override
    public BZServerConfiguration find(final String _url) {

        Validate.notNull(_url);
        return CollectionUtil.singleOrNull(configurationStorage
                .find(new SearchFilterImplementation(_url)));
    }

    /**
     * Method getPluginStorage.
     *
     * @return IPluginStorageService
     * @see
     * org.komea.backend.IBZConfigurationDAO.bugzilla.api.IBugZillaConfigurationService#getPluginStorage()
     */
    @Override
    public IPluginStorageService getPluginStorage() {

        return pluginStorage;
    }

    /**
     * Method getServerProxyFactory.
     *
     * @return IBZServerProxyFactory
     * @see
     * org.komea.backend.IBZConfigurationDAO.bugzilla.api.IBugZillaConfigurationService#getServerProxyFactory()
     */
    @Override
    public IBZServerProxyFactory getServerProxyFactory() {

        return serverProxyFactory;
    }

    @PostConstruct
    public void init() {

        configurationStorage
                = pluginStorage.registerDAOStorage("BUGZILLA", BZServerConfiguration.class);

    }

    @Override
    public void saveOrUpdate(final BZServerConfiguration server) {

        Validate.notNull(server);
        configurationStorage.saveOrUpdate(server);
    }

    @Override
    public List<BZServerConfiguration> selectAll() {

        return configurationStorage.selectAll();
    }

    /**
     * Method setPluginStorage.
     *
     * @param pluginStorage IPluginStorageService
     * @see
     * org.komea.backend.IBZConfigurationDAO.bugzilla.api.IBugZillaConfigurationService#setPluginStorage(IPluginStorageService)
     */
    @Override
    public void setPluginStorage(final IPluginStorageService pluginStorage) {

        this.pluginStorage = pluginStorage;
    }

    /**
     * Method setServerProxyFactory.
     *
     * @param serverProxyFactory IBZServerProxyFactory
     * @see
     * org.komea.backend.IBZConfigurationDAO.bugzilla.api.IBugZillaConfigurationService#setServerProxyFactory(IBZServerProxyFactory)
     */
    @Override
    public void setServerProxyFactory(final IBZServerProxyFactory serverProxyFactory) {

        this.serverProxyFactory = serverProxyFactory;
    }

    @Override
    public boolean testConnexion(final BZServerConfiguration server) throws IOException, ConnectionException, BugzillaException {

        final IBZServerProxy newConnector = serverProxyFactory.newTestConnector(server);

        boolean connexion = false;
        if (newConnector != null && newConnector.testConnexion()) {
            connexion = true;
            newConnector.close();
        }
        return connexion;
    }

}
