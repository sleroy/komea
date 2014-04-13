/**
 * 
 */

package org.komea.eventory.cache;



import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.komea.product.cep.api.cache.ICacheConfiguration;
import org.komea.product.cep.cache.guava.GoogleCacheStorage;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



/**
 * @author sleroy
 */

public class GoogleCacheStorageBenchTest
{
    
    
    public static ICacheConfiguration buildFakeCacheConfiguration() {
    
    
        final ICacheConfiguration mock = mock(ICacheConfiguration.class);
        when(mock.getTimeUnit()).thenReturn(TimeUnit.DAYS);
        when(mock.getTime()).thenReturn(4);
        when(mock.hasTimePolicy()).thenReturn(true);
        when(mock.hasMaximumSizePolicy()).thenReturn(false);
        return mock;
    }
    
    
    public static ICacheConfiguration buildMaxSize(final int _i) {
    
    
        final ICacheConfiguration mock = mock(ICacheConfiguration.class);
        when(mock.getMaximumSize()).thenReturn(Integer.valueOf(1));
        when(mock.hasMaximumSizePolicy()).thenReturn(true);
        when(mock.hasTimePolicy()).thenReturn(false);
        return mock;
    }
    
    
    
    @Rule
    public TestRule benchmarkRun = new BenchmarkRule();
    
    
    
    /**
     * Test method for
     * {@link org.komea.eventory.cache.GoogleCacheStorage#GoogleCacheStorage(org.komea.eventory.api.cache.ICacheConfiguration)}.
     */
    @BenchmarkOptions(
        warmupRounds = 30,
        benchmarkRounds = 200)
    @Test
    public final void testGoogleCacheStorage() throws Exception {
    
    
        final ICacheConfiguration mock = buildFakeCacheConfiguration();
        
        new GoogleCacheStorage<Serializable>(mock);
        
        
    }
    
}
