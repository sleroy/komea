/**
 * 
 */

package org.komea.product.cep.cache;



import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;



/**
 * @author sleroy
 */

public class GoogleCacheStorageBenchTest
{
    
    
    @Rule
    public TestRule benchmarkRun = new BenchmarkRule();
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.cep.cache.GoogleCacheStorage#GoogleCacheStorage(org.komea.product.cep.api.cache.ICacheConfiguration)}.
     */
    @BenchmarkOptions(warmupRounds = 30, benchmarkRounds = 200)
    @Test
    public final void testGoogleCacheStorage() throws Exception {
    
    
        new GoogleCacheStorage<Serializable>(CacheConfigurationBuilder.expirationTimeCache(4,
                TimeUnit.DAYS));
        
    }
    
}
