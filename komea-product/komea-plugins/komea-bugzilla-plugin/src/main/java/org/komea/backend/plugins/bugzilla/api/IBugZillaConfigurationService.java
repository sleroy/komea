/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.bugzilla.api;

import java.util.List;
import org.komea.backend.plugins.bugzilla.api.IBugZillaServerConfiguration;
import org.komea.backend.plugins.bugzilla.api.IBugZillaServerProxyFactory;
import org.komea.backend.plugins.bugzilla.data.BugZillaServer;

import org.komea.product.backend.service.plugins.IPluginStorageService;

/**
 *
 * @author rgalerme
 */
public interface IBugZillaConfigurationService {

    /**
     *
     * @param server
     * @return
     */
    public void saveOrUpdate(BugZillaServer server,String oldAddress);
    
    /**
     * 
     * @return 
     */
    public List<BugZillaServer> selectAll();
    
    /**
     * 
     * @return 
     */
    public List<IBugZillaServerConfiguration> getServers();

    /**
     * 
     * @return 
     */
           
    public IPluginStorageService getPluginStorage();

    /**
     * 
     * @param pluginStorage 
     */
            
    public void setPluginStorage(IPluginStorageService pluginStorage);

    /**
     * 
     * @return 
     */
    public IBugZillaServerProxyFactory getServerProxyFactory();

    /**
     * 
     * @param serverProxyFactory 
     */
    public void setServerProxyFactory(IBugZillaServerProxyFactory serverProxyFactory);
}
