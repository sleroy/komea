
package org.komea.product.backend.service;



import java.util.Map;

import org.komea.product.backend.business.IDAOObjectStorage;
import org.komea.product.backend.service.fs.IKomeaFS;
import org.komea.product.backend.service.fs.IObjectStorage;
import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.komea.product.backend.storage.DAOObjectStorage;
import org.komea.product.backend.storage.ObjectStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;



/**
 * This plugin Storage Service.
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */
@Service
public class PluginStorageService implements IPluginStorageService
{
    
    
    @Autowired
    private IKomeaFS                  komeaFS;
    
    private final Map<String, Object> storages = Maps.newConcurrentMap();
    
    
    
    /**
     * Method getKomeaFS.
     * 
     * @return IKomeaFS
     */
    public IKomeaFS getKomeaFS() {
    
    
        return komeaFS;
    }
    
    
    /**
     * Method registerDAOStorage.
     * 
     * @param _pluginName
     *            String
     * @param _pojoStorageClass
     *            Class<T>
     * @return IDAOObjectStorage<T>
     * @see org.komea.product.backend.service.IPluginStorageService#registerDAOStorage(String, Class<T>)
     */
    @Override
    public <T> IDAOObjectStorage<T> registerDAOStorage(
            final String _pluginName,
            final Class<T> _pojoStorageClass) {
    
    
        IDAOObjectStorage<T> object = (IDAOObjectStorage<T>) storages.get(_pluginName);
        if (object == null) {
            object = new DAOObjectStorage(registerStorage(_pluginName, _pojoStorageClass));
            storages.put(_pluginName + _pojoStorageClass.getName(), object);
        }
        return object;
    }
    
    
    /**
     * Method registerStorage.
     * 
     * @param _pluginName
     *            String
     * @param _pojoStorageClass
     *            Class<T>
     * @return IObjectStorage<T>
     * @see org.komea.product.backend.service.IPluginStorageService#registerStorage(String, Class<T>)
     */
    @Override
    public synchronized <T> IObjectStorage<T> registerStorage(
            final String _pluginName,
            final Class<T> _pojoStorageClass) {
    
    
        IObjectStorage<T> object = (IObjectStorage<T>) storages.get(_pluginName);
        if (object == null) {
            object = new ObjectStorage<T>(komeaFS.getFileSystem(_pluginName), _pojoStorageClass);
            storages.put(_pluginName + _pojoStorageClass.getName(), object);
        }
        return object;
    }
    
    
    /**
     * Method setKomeaFS.
     * 
     * @param _komeaFS
     *            IKomeaFS
     */
    public void setKomeaFS(final IKomeaFS _komeaFS) {
    
    
        komeaFS = _komeaFS;
    }
}
