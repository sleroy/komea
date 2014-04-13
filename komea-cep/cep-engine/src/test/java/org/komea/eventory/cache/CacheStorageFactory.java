/**
 * 
 */

package org.komea.eventory.cache;



import org.komea.eventory.api.cache.ICacheStorageFactory;
import org.komea.product.cep.api.cache.ICacheConfiguration;
import org.komea.product.cep.api.cache.ICacheStorage;



/**
 * @author sleroy
 */
public class CacheStorageFactory implements ICacheStorageFactory
{
    
    
    /**
     * 
     */
    public CacheStorageFactory() {
    
    
        // TODO Auto-generated constructor stub
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.cache.ICacheStorageFactory#newCacheStorage(org.komea.product.cep.api.cache.ICacheConfiguration)
     */
    @Override
    public ICacheStorage newCacheStorage(final ICacheConfiguration _cacheConfiguration) {
    
    
        return new GoogleCacheStorage<>(_cacheConfiguration);
    }
    
}
