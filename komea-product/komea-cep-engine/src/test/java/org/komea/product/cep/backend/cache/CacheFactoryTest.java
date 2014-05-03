/**
 * 
 */

package org.komea.product.cep.backend.cache;



import org.junit.Test;
import org.komea.eventory.api.cache.ICacheStorage;
import org.komea.eventory.cache.CacheConfigurationBuilder;
import org.komea.eventory.cache.guava.GoogleCacheStorage;
import org.komea.product.cep.backend.cache.CacheFactory;

import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */
public class CacheFactoryTest
{
    
    
    /**
     * Test method for {@link org.komea.product.cep.backend.cache.CacheFactory#newCacheStorage(org.komea.eventory.api.cache.ICacheConfiguration)}.
     */
    @Test
    public final void testNewCacheStorage() throws Exception {
    
    
        final CacheFactory cacheFactory = new CacheFactory();
        cacheFactory.setImplementation(GoogleCacheStorage.class.getName());
        cacheFactory.init();
        final ICacheStorage cacheStorage =
                cacheFactory.newCacheStorage(CacheConfigurationBuilder.noConfiguration());
        assertTrue(cacheStorage instanceof GoogleCacheStorage);
    }
}
