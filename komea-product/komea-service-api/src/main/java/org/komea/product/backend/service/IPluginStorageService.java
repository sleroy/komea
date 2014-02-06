
package org.komea.product.backend.service;



import org.komea.product.backend.fs.IObjectStorage;
import org.komea.product.backend.service.business.IDAOObjectStorage;



/**
 * This interface defines the plugin storage service.
 * 
 * @author sleroy
 */
public interface IPluginStorageService
{
    
    
    /**
     * This methods register an object storage in the plugin storage service
     * 
     * @param _pluginName
     *            the plugin name
     * @param _pojoStorageClass
     *            the class of the pojo to be stored/retrieved
     * @return the object stoirage
     */
    <T> IDAOObjectStorage<T> registerDAOStorage(String _pluginName, Class<T> _pojoStorageClass);
    
    
    /**
     * This methods register an object storage in the plugin storage service
     * 
     * @param _pluginName
     *            the plugin name
     * @param _pojoStorageClass
     *            the class of the pojo to be stored/retrieved
     * @return the object stoirage
     */
    <T> IObjectStorage<T> registerStorage(String _pluginName, Class<T> _pojoStorageClass);
}
