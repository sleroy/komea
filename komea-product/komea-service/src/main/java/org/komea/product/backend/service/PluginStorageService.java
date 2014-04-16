
package org.komea.product.backend.service;



import java.util.Map;

import org.komea.product.backend.business.IDAOObjectStorage;
import org.komea.product.backend.service.fs.IKomeaFS;
import org.komea.product.backend.service.fs.IObjectStorage;
import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.komea.product.backend.storage.DAOObjectStorage;
import org.komea.product.backend.storage.ObjectStorage;
import org.komea.product.database.api.IHasId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    
    
    private static final Logger       LOGGER   = LoggerFactory.getLogger("plugin-storage");
    
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
    public <T extends IHasId> IDAOObjectStorage<T> registerDAOStorage(
            final String _pluginName,
            final Class<T> _pojoStorageClass) {
    
    
        LOGGER.debug("Registering DAO storage for {} and object of type {}", _pluginName,
                _pojoStorageClass.getName());
        
        final String storageName = getStorageName(_pluginName, _pojoStorageClass);
        IDAOObjectStorage<T> object = getDaoStorage(storageName);
        if (object == null) {
            object = new DAOObjectStorage(registerStorage(_pluginName, _pojoStorageClass));
            putDAOStorage(storageName, object);
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
    public synchronized <T extends IHasId> IObjectStorage<T> registerStorage(
            final String _pluginName,
            final Class<T> _pojoStorageClass) {
    
    
        LOGGER.debug("Registering storage for {} and object of type {}", _pluginName,
                _pojoStorageClass.getName());
        final String storageName = getStorageName(_pluginName, _pojoStorageClass);
        IObjectStorage<T> object = getObjectStorage(storageName);
        if (object == null) {
            object = new ObjectStorage<T>(komeaFS.getFileSystem(_pluginName), _pojoStorageClass);
            putObjectStorage(storageName, object);
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
    
    
    private IDAOObjectStorage getDaoStorage(final String storageName) {
    
    
        return (IDAOObjectStorage) storages.get(storageName);
    }
    
    
    private <T extends IHasId> IObjectStorage<T> getObjectStorage(final String storageName) {
    
    
        return (IObjectStorage<T>) storages.get(storageName);
    }
    
    
    private <T extends IHasId> String getStorageName(
            final String _pluginName,
            final Class<T> _pojoStorageClass) {
    
    
        return _pluginName + _pojoStorageClass.getName();
    }
    
    
    private <T extends IHasId> void putDAOStorage(
            final String storageName,
            final IDAOObjectStorage<T> object) {
    
    
        storages.put(storageName, object);
    }
    
    
    private <T extends IHasId> void putObjectStorage(
            final String storageName,
            final IObjectStorage<T> object) {
    
    
        storages.put(storageName, object);
    }
}
