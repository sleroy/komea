/**
 * 
 */

package org.komea.eventory.utils;



import org.komea.eventory.api.cache.ICacheStorageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This class defines utility methods for plugins.
 * 
 * @author sleroy
 */
public class PluginUtils
{
    
    
    private static ICacheStorageFactory cacheStorageFactory = null;
    
    private static final Logger         LOGGER              =
                                                                    LoggerFactory
                                                                            .getLogger("eventory-plugin-service");
    
    
    
    public static ICacheStorageFactory getCacheStorageFactory() {
    
    
        return cacheStorageFactory;
    }
    
    
    public static void setCacheStorageFactory(final ICacheStorageFactory _cacheStorageFactory) {
    
    
        cacheStorageFactory = _cacheStorageFactory;
    }
    
    
}
