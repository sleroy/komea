/**
 * 
 */

package org.komea.eventory.cache.guava;


import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.eventory.api.cache.ICacheConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class GoogleCacheStorageTest {
    
    @Mock
    private ICacheConfiguration        cacheConfiguration;
    @InjectMocks
    private GoogleCacheStorage<String> googleCacheStorage;
    
    /**
     * Test method for {@link org.komea.eventory.cache.guava.GoogleCacheStorage#clear()}.
     */
    @Test
    public final void testClear() throws Exception {
    
        googleCacheStorage.push("string");
        googleCacheStorage.push("string2");
        Assert.assertEquals(2, googleCacheStorage.size());
        googleCacheStorage.clear();
    }
    
    /**
     * Test method for {@link org.komea.eventory.cache.guava.GoogleCacheStorage#getAllValues()}.
     */
    @Test
    public final void testGetAllValues() throws Exception {
    
        googleCacheStorage.push("string");
        googleCacheStorage.push("string2");
        Assert.assertEquals(2, googleCacheStorage.getAllValues().size());
        Assert.assertTrue(googleCacheStorage.getAllValues().contains("string"));
        Assert.assertTrue(googleCacheStorage.getAllValues().contains("string2"));
    }
    
    /**
     * Test method for {@link org.komea.eventory.cache.guava.GoogleCacheStorage#getIterator()}.
     */
    @Test
    public final void testGetIterator() throws Exception {
    
        googleCacheStorage.push("string");
        googleCacheStorage.push("string2");
        final Iterator<String> iterator = googleCacheStorage.getIterator();
        int i = 0;
        while (iterator.hasNext()) {
            iterator.next();
            i++;
        }
        Assert.assertEquals(2, i);
    }
    
    /**
     * Test method for
     * {@link org.komea.eventory.cache.guava.GoogleCacheStorage#GoogleCacheStorage(org.komea.eventory.api.cache.ICacheConfiguration)}.
     */
    @Test
    public final void testGoogleCacheStorage() throws Exception {
    
        Assert.assertTrue(googleCacheStorage.getAllValues().isEmpty());
        Assert.assertFalse(googleCacheStorage.getIterator().hasNext());
        Assert.assertEquals(0, googleCacheStorage.size());
        
    }
    
    /**
     * Test method for {@link org.komea.eventory.cache.guava.GoogleCacheStorage#push(java.io.Serializable)}.
     */
    @Test
    public final void testPush() throws Exception {
    
        googleCacheStorage.push("string");
        googleCacheStorage.push("string2");
        googleCacheStorage.push("string3");
        
    }
    
    /**
     * Test method for {@link org.komea.eventory.cache.guava.GoogleCacheStorage#size()}.
     */
    @Test
    public final void testSize() throws Exception {
    
        googleCacheStorage.push("string");
        googleCacheStorage.push("string2");
        googleCacheStorage.push("string3");
        Assert.assertEquals(3, googleCacheStorage.getAllValues().size());
    }
    
}
