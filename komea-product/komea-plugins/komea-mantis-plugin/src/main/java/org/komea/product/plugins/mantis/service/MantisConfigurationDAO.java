/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.mantis.service;



import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.Validate;
import org.komea.product.backend.business.IDAOObjectStorage;
import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.backend.utils.SearchFilter;
import org.komea.product.plugins.mantis.api.IMantisConfigurationDAO;
import org.komea.product.plugins.mantis.api.IMantisServerProxy;
import org.komea.product.plugins.mantis.api.IMantisServerProxyFactory;
import org.komea.product.plugins.mantis.model.MantisServerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * @author rgalerme
 * @version $Revision: 1.0 $
 */
@Service
public class MantisConfigurationDAO implements IMantisConfigurationDAO
{
    
    
    /**
     * @author sleroy
     */
    private final class SearchFilterImplementation implements
            SearchFilter<MantisServerConfiguration>
    {
        
        
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
        public boolean match(final MantisServerConfiguration _object) {
        
        
            return string.equals(_object.getAddress());
        }
    }
    
    
    
    private IDAOObjectStorage<MantisServerConfiguration> configurationStorage;
    
    @Autowired
    private IPluginStorageService                        pluginStorage;
    
    @Autowired
    private IMantisServerProxyFactory                    serverProxyFactory;
    
    
    
    @Override
    public boolean delete(final MantisServerConfiguration _object) {
    
    
        return configurationStorage.delete(_object);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.mantis.api.IMantisConfigurationDAO#find(java.lang.String)
     */
    @Override
    public MantisServerConfiguration find(final String _url) {
    
    
        Validate.notNull(_url);
        return CollectionUtil.singleOrNull(configurationStorage
                .find(new SearchFilterImplementation(_url)));
    }
    
    
    /**
     * Method getPluginStorage.
     * 
     * @return IPluginStorageService
     * @see org.komea.backend.IMantisConfigurationDAO.bugzilla.api.IBugZillaConfigurationService#getPluginStorage()
     */
    @Override
    public IPluginStorageService getPluginStorage() {
    
    
        return pluginStorage;
    }
    
    
    /**
     * Method getServerProxyFactory.
     * 
     * @return IMantisServerProxyFactory
     * @see org.komea.backend.IMantisConfigurationDAO.bugzilla.api.IBugZillaConfigurationService#getServerProxyFactory()
     */
    @Override
    public IMantisServerProxyFactory getServerProxyFactory() {
    
    
        return serverProxyFactory;
    }
    
    
    @PostConstruct
    public void init() {
    
    
        configurationStorage =
                pluginStorage.registerDAOStorage("BUGZILLA", MantisServerConfiguration.class);
        
    }
    
    
    @Override
    public void saveOrUpdate(final MantisServerConfiguration server) {
    
    
        Validate.notNull(server);
        configurationStorage.saveOrUpdate(server);
    }
    
    
    @Override
    public List<MantisServerConfiguration> selectAll() {
    
    
        return configurationStorage.selectAll();
    }
    
    
    /**
     * Method setPluginStorage.
     * 
     * @param pluginStorage
     *            IPluginStorageService
     * @see org.komea.backend.IMantisConfigurationDAO.bugzilla.api.IBugZillaConfigurationService#setPluginStorage(IPluginStorageService)
     */
    @Override
    public void setPluginStorage(final IPluginStorageService pluginStorage) {
    
    
        this.pluginStorage = pluginStorage;
    }
    
    
    /**
     * Method setServerProxyFactory.
     * 
     * @param serverProxyFactory
     *            IMantisServerProxyFactory
     * @see org.komea.backend.IMantisConfigurationDAO.bugzilla.api.IBugZillaConfigurationService#setServerProxyFactory(IMantisServerProxyFactory)
     */
    
    public void setServerProxyFactory(final IMantisServerProxyFactory serverProxyFactory) {
    
    
        this.serverProxyFactory = serverProxyFactory;
    }
    
    
    @Override
    public boolean testConnexion(final MantisServerConfiguration server) {
    
    
        final IMantisServerProxy newConnector = serverProxyFactory.newTestConnector(server);
        
        boolean connexion = false;
        if (newConnector != null && newConnector.testConnexion()) {
            connexion = true;
            try {
                newConnector.close();
            } catch (final IOException ex) {
                connexion = false;
            }
        }
        return connexion;
    }
    
}
