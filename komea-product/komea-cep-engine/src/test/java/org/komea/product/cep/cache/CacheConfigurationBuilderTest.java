/**
 * 
 */

package org.komea.product.cep.cache;



import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.cep.api.cache.ICacheConfiguration;
import org.komea.product.cep.cache.CacheConfigurationBuilder;



/**
 * @author sleroy
 */
public class CacheConfigurationBuilderTest
{
    
    
    /**
     * Test method for {@link org.komea.product.cep.cache.CacheConfigurationBuilder#expirationTime(int, java.util.concurrent.TimeUnit)}.
     */
    @Test @Ignore
    public final void testExpirationTime() throws Exception {
    
    
        final ICacheConfiguration build =
                CacheConfigurationBuilder.create().expirationTime(1, TimeUnit.MINUTES).build();
        
        Assert.assertEquals("Cache should be initialized with 1", Integer.valueOf(1),
                build.getTime());
        Assert.assertEquals("Cache should be initialized with minute time unit", TimeUnit.MINUTES,
                build.getTimeUnit());
        Assert.assertTrue(build.hasTimePolicy());
        Assert.assertFalse(build.hasNoPolicy());
        Assert.assertFalse(build.hasMaximumSizePolicy());
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.cache.CacheConfigurationBuilder#maximumSize(int)}.
     */
    @Test @Ignore
    public final void testMaximumSize() throws Exception {
    
    
        final ICacheConfiguration build =
                CacheConfigurationBuilder.create().maximumSize(10).build();
        Assert.assertEquals("Maximum size should be set", Integer.valueOf(10),
                build.getMaximumSize());
        Assert.assertFalse(build.hasTimePolicy());
        Assert.assertFalse(build.hasNoPolicy());
        Assert.assertTrue(build.hasMaximumSizePolicy());
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.cache.CacheConfigurationBuilder#build()}.
     */
    @Test @Ignore
    public final void testNoConfiguration() throws Exception {
    
    
        final ICacheConfiguration build = CacheConfigurationBuilder.create().build();
        Assert.assertNotNull("Cache should be initialized with no configuration", build);
        Assert.assertFalse(build.hasTimePolicy());
        Assert.assertTrue(build.hasNoPolicy());
        Assert.assertFalse(build.hasMaximumSizePolicy());
        
    }
    
    
}
