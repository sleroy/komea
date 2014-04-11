/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.bugzilla.api;



import java.util.List;

import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;



/**
 * @author rgalerme
 */
public interface IBZConfigurationDAO
{
    
    
    public boolean delete(BZServerConfiguration _address);
    
    
    /**
     * @param _string
     * @return
     */
    public BZServerConfiguration find(String _string);
    
    
    /**
     * @return
     */
    
    public IPluginStorageService getPluginStorage();
    
    
    /**
     * @return
     */
    public IBZServerProxyFactory getServerProxyFactory();
    
    
    /**
     * @param server
     * @return
     */
    public void saveOrUpdate(BZServerConfiguration server);
    
    
    /**
     * @return
     */
    public List<BZServerConfiguration> selectAll();
    
    
    /**
     * @param pluginStorage
     */
    
    public void setPluginStorage(IPluginStorageService pluginStorage);
    
    
    /**
     * @param serverProxyFactory
     */
    public void setServerProxyFactory(IBZServerProxyFactory serverProxyFactory);
}
