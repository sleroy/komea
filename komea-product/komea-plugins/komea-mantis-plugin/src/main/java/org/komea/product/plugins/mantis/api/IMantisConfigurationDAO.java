/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.mantis.api;



import java.util.List;

import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.komea.product.plugins.mantis.model.MantisServerConfiguration;



/**
 * @author rgalerme
 */
public interface IMantisConfigurationDAO
{
    
    
    public boolean delete(MantisServerConfiguration _address);
    
    
    /**
     * @return the server configuration corresponding to the server name.
     */
    public MantisServerConfiguration find(String _serverName);
    
    
    public IPluginStorageService getPluginStorage();
    
    
    public IMantisServerProxyFactory getServerProxyFactory();
    
    
    public void saveOrUpdate(MantisServerConfiguration server);
    
    
    public List<MantisServerConfiguration> selectAll();
    
    
    public void setPluginStorage(IPluginStorageService pluginStorage);
    
    
    public boolean testConnexion(MantisServerConfiguration server);
}
