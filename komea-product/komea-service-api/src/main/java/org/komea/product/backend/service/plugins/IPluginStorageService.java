
package org.komea.product.backend.service.plugins;



import org.komea.product.backend.business.IDAOObjectStorage;
import org.komea.product.backend.service.fs.IObjectStorage;
import org.komea.product.database.api.IHasId;



/**
 * This interface defines the plugin storage service.
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
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
    <T extends IHasId> IDAOObjectStorage<T> registerDAOStorage(
            String _pluginName,
            Class<T> _pojoStorageClass);
    
    
    /**
     * This methods register an object storage in the plugin storage service
     * 
     * @param _pluginName
     *            the plugin name
     * @param _pojoStorageClass
     *            the class of the pojo to be stored/retrieved
     * @return the object stoirage
     */
    <T extends IHasId> IObjectStorage<T> registerStorage(
            String _pluginName,
            Class<T> _pojoStorageClass);
}
