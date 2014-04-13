/**
 * 
 */

package org.komea.eventory.utils;



import java.io.IOException;
import java.util.Properties;

import jodd.util.PropertiesUtil;

import org.komea.eventory.api.bridge.IEventBridgeFactory;
import org.komea.eventory.api.cache.ICacheStorageFactory;
import org.komea.eventory.api.errors.PluginImplementationNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This class defines utility methods for plugins.
 * 
 * @author sleroy
 */
public class PluginUtils
{
    
    
    public static final String          BRIDGE_IMPL         = "bridgeClass";
    
    public static final String          CACHEFACTORY_IMPL   = "cacheFactoryClass";
    
    public static final String          EVENTORY_PROPERTIES = "/eventory.properties";
    
    public static final String          EVENTORY_SYSTEM_VAR = "eventory.configFile";
    
    private static IEventBridgeFactory  bridgeFactory       = null;
    
    private static ICacheStorageFactory cacheStorageFactory = null;
    
    private static final Logger         LOGGER              =
                                                                    LoggerFactory
                                                                            .getLogger("eventory-plugin-service");
    
    private static Properties           properties;
    
    static {
        try {
            properties = new Properties();
            final String property = System.getProperty(EVENTORY_SYSTEM_VAR);
            if (property != null) {
                try {
                    properties = PropertiesUtil.createFromFile(property);
                } catch (final IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
            if (properties != null) {
                properties = PropertiesUtil.createFromClasspath(EVENTORY_PROPERTIES);
            }
        } catch (final Throwable eThrowable) {
            LOGGER.error(eThrowable.getMessage(), eThrowable);
        }
    }
    
    
    
    public static IEventBridgeFactory getBridgeFactory() {
    
    
        return bridgeFactory;
    }
    
    
    public static ICacheStorageFactory getCacheStorageFactory() {
    
    
        return cacheStorageFactory;
    }
    
    
    /**
     * Requests an instance of the implementation of the given interface.
     * 
     * @param _pluginInterface
     *            the interface
     * @return the instance or null.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getImplementation(final String _className) {
    
    
        LOGGER.debug("Request the implementation plugin for {}", _className);
        try {
            return (T) ClassUtils.instantiate(Thread.currentThread().getContextClassLoader()
                    .loadClass(_className));
        } catch (final ClassNotFoundException e) {
            throw new PluginImplementationNotFoundException(_className, e);
        }
        
    }
    
    
    public static void setBridgeFactory(final IEventBridgeFactory _bridgeFactory) {
    
    
        bridgeFactory = _bridgeFactory;
    }
    
    
    public static void setCacheStorageFactory(final ICacheStorageFactory _cacheStorageFactory) {
    
    
        cacheStorageFactory = _cacheStorageFactory;
    }
    
    
}
