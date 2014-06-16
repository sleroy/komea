
package org.komea.eventory.cache.guava;



import org.komea.eventory.api.cache.ICacheConfiguration;
import org.komea.eventory.api.cache.ICacheStorage;
import org.komea.eventory.api.cache.ICacheStorageFactory;



/**
 * @author sleroy
 */
public class CacheStorageFactory implements ICacheStorageFactory
{


    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.cache.ICacheStorageFactory#newCacheStorage(org.komea.product.cep.api.cache.ICacheConfiguration)
     */
    @Override
    public ICacheStorage newCacheStorage(final ICacheConfiguration _cacheConfiguration) {
    
    
        return new GoogleCacheStorage<>(_cacheConfiguration);
    }
    
}
