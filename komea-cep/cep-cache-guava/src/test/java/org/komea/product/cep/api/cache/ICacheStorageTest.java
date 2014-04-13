/**
 * 
 */

package org.komea.product.cep.api.cache;



import org.junit.Assert;
import org.junit.Test;
import org.komea.eventory.api.cache.ICacheStorage;
import org.komea.eventory.cache.GoogleCacheStorageBenchTest;
import org.komea.product.cep.cache.guava.GoogleCacheStorage;



/**
 * @author sleroy
 */
public class ICacheStorageTest
{
    
    
    /**
     * Test method for {@link org.komea.eventory.api.cache.ICacheStorage#getIterator()}.
     */
    @Test
    public void testGetIterator() throws Exception {
    
    
        final GoogleCacheStorage<Integer> cacheStorage =
                new GoogleCacheStorage<Integer>(
                        GoogleCacheStorageBenchTest.buildFakeCacheConfiguration());
        cacheStorage.push(1);
        cacheStorage.push(2);
        cacheStorage.push(3);
        Assert.assertEquals("Expected 3 items ", 3, cacheStorage.size());
    }
    
    
    /**
     * Test method for {@link org.komea.eventory.api.cache.ICacheStorage#push(java.lang.Object)}.
     */
    @Test
    public void testPushWithMaximumSize1() throws Exception {
    
    
        final GoogleCacheStorage<Integer> cacheStorage =
                new GoogleCacheStorage<Integer>(GoogleCacheStorageBenchTest.buildMaxSize(1));
        cacheStorage.push(1);
        cacheStorage.push(2);
        cacheStorage.push(3);
        Assert.assertEquals("Expected 1 items ", 1, cacheStorage.size());
    }
    
    
}
