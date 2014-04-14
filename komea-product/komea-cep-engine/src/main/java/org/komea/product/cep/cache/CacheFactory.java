/**
 * 
 */

package org.komea.product.cep.cache;



import java.lang.reflect.Constructor;

import javax.annotation.PostConstruct;

import org.komea.eventory.api.cache.ICacheConfiguration;
import org.komea.eventory.api.cache.ICacheStorage;
import org.komea.eventory.api.cache.ICacheStorageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



/**
 * This class defines the cache factory
 * 
 * @author sleroy
 */
@Service
public class CacheFactory implements ICacheStorageFactory
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheFactory.class);
    private Constructor<?>      constructor;
    
    
    @Value(
        value = "#{config.cacheImplementation}")
    private String              implementation;
    
    
    
    /**
     * 
     */
    public CacheFactory() {
    
    
        super();
    }
    
    
    @PostConstruct
    public void init() {
    
    
        LOGGER.info("Cache Factory implementation is {}", implementation);
        
        try {
            final Class<?> implemClass =
                    Thread.currentThread().getContextClassLoader().loadClass(implementation);
            constructor = implemClass.getConstructor(ICacheConfiguration.class);
            
            
        } catch (final Exception e) {
            throw new BeanInitializationException(e.getMessage(), e);
        }
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.cache.ICacheStorageFactory#newCacheStorage(org.komea.eventory.api.cache.ICacheConfiguration)
     */
    @Override
    public ICacheStorage newCacheStorage(final ICacheConfiguration _arg0) {
    
    
        try {
            return (ICacheStorage<?>) constructor.newInstance(_arg0);
        } catch (final Exception e) {
            throw new BeanInitializationException(e.getMessage(), e);
        }
    }
}
