
package org.komea.product.backend.service;



import org.komea.product.backend.fs.IObjectStorage;
import org.komea.product.backend.service.business.IDAOObjectStorage;
import org.komea.product.backend.storage.DAOStorage;
import org.komea.product.backend.storage.ObjectStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * This plugin Storage Service.
 * 
 * @author sleroy
 */
@Service
public class PluginStorageService implements IPluginStorageService
{
    
    
    @Autowired
    private IKomeaFS komeaFS;
    
    
    
    public IKomeaFS getKomeaFS() {
    
    
        return komeaFS;
    }
    
    
    @Override
    public <T> IDAOObjectStorage<T> registerDAOStorage(
            final String _pluginName,
            final Class<T> _pojoStorageClass) {
    
    
        return new DAOStorage(registerStorage(_pluginName, _pojoStorageClass));
    }
    
    
    @Override
    public <T> IObjectStorage<T> registerStorage(
            final String _pluginName,
            final Class<T> _pojoStorageClass) {
    
    
        return new ObjectStorage(komeaFS.getFileSystem(_pluginName), _pojoStorageClass);
    }
    
    
    public void setKomeaFS(final IKomeaFS _komeaFS) {
    
    
        komeaFS = _komeaFS;
    }
}
