/**
 * 
 */

package org.komea.product.cep.backend.cache;



import org.junit.Test;
import org.komea.eventory.api.cache.ICacheStorage;
import org.komea.eventory.cache.CacheConfigurationBuilder;
import org.komea.eventory.cache.guava.GoogleCacheStorage;
import org.komea.product.cep.backend.cache.CacheStorageFactoryService;

import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */
public class CacheStorageFactoryServiceTest
{
    
    
    /**
     * Test method for {@link org.komea.product.cep.backend.cache.CacheStorageFactoryService#newCacheStorage(org.komea.eventory.api.cache.ICacheConfiguration)}.
     */
    @Test
    public final void testNewCacheStorage() throws Exception {
    
    
        final CacheStorageFactoryService cacheStorageFactoryService = new CacheStorageFactoryService();
        cacheStorageFactoryService.setImplementation(GoogleCacheStorage.class.getName());
        cacheStorageFactoryService.init();
        final ICacheStorage cacheStorage =
                cacheStorageFactoryService.newCacheStorage(CacheConfigurationBuilder.noConfiguration());
        assertTrue(cacheStorage instanceof GoogleCacheStorage);
    }
}
