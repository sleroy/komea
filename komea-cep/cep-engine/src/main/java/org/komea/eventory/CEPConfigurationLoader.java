/**
 *
 */

package org.komea.eventory;



import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.komea.eventory.api.bridge.IEventBridgeFactory;
import org.komea.eventory.api.cache.ICacheStorageFactory;
import org.komea.eventory.api.engine.RunningMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author sleroy
 */
public class CEPConfigurationLoader
{
    
    
    private static final String    BRIDGE_FACTORY         = "bridge.factory";
    
    
    private static final String    CACHESTORAGE_FACTORY   = "cachestorage.factory";
    
    
    private static final String    EVENTORY_PROPERTIES    = "eventory.properties";
    
    private static final Logger    LOGGER                 =
                                                                  LoggerFactory
                                                                          .getLogger(CEPConfigurationLoader.class);
    
    private static final String    NUMBER_QUERY_LISTENERS = "numberQueryListeners";
    
    private static final String    RUNNING_MODE           = "runningMode";
    
    private final CEPConfiguration configuration;
    
    
    
    public CEPConfigurationLoader(final CEPConfiguration _configuration) {
    
    
        configuration = _configuration;
        
        
    }
    
    
    public void load() {
    
    
        final Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(EVENTORY_PROPERTIES));
            //
            initBridge(properties);
            initCacheStorageFactory(properties);
            final String runningMode = properties.getProperty(RUNNING_MODE);
            configuration.setMode(RunningMode.valueOf(runningMode));
            final String numListeners = properties.getProperty(NUMBER_QUERY_LISTENERS);
            configuration.setNumberQueryListeners(Integer.parseInt(numListeners));
            for (final Entry<Object, Object> entry : properties.entrySet()) {
                configuration.put(entry.getKey().toString(), entry.getValue().toString());
            }
            configuration.setStorageFolder(new File(properties.getProperty("storage.folder")));
        } catch (final Exception e) {
            LOGGER.error("Could not load properties from eventories.properties, please check the resources.");
            throw new RuntimeException("Could not find eventory.properties", e);
        }
        
    }


    private void initBridge(final Properties _properties) throws Exception {


        final String bridgeProperty = _properties.getProperty(BRIDGE_FACTORY, "no bridge defined");
        final Object instance =
                ConstructorUtils.invokeConstructor(ClassUtils.getClass(bridgeProperty));
        configuration.setBridgeFactory((IEventBridgeFactory) instance);

    }
    
    
    private void initCacheStorageFactory(final Properties properties)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, ClassNotFoundException {
    
    
        final String property = properties.getProperty(CACHESTORAGE_FACTORY);
        final Class<?> class1 = ClassUtils.getClass(property);
        configuration.setCacheStorageFactory((ICacheStorageFactory) ConstructorUtils
                .invokeConstructor(class1));
    }
    
}
