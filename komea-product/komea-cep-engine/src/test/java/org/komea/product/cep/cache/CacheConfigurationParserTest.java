/**
 * 
 */

package org.komea.product.cep.cache;



import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.komea.eventory.api.cache.ICacheConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;



/**
 * @author sleroy
 */
public class CacheConfigurationParserTest
{
    
    
    /**
     * Test method for {@link org.komea.product.cep.cache.CacheConfigurationParser#parse(java.lang.String)}.
     */
    @Test
    public final void testParseDays() throws Exception {
    
    
        final ICacheConfiguration parse = new CacheConfigurationParser().parse("days(1)");
        assertNotNull(parse);
        assertNull(parse.getMaximumSize());
        assertEquals(Integer.valueOf(1), parse.getTime());
        assertEquals(TimeUnit.DAYS, parse.getTimeUnit());
        
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.cache.CacheConfigurationParser#parse(java.lang.String)}.
     */
    @Test
    public final void testParseEmpty() throws Exception {
    
    
        final ICacheConfiguration parse =
                new CacheConfigurationParser().parse("#" + CacheConfigurationParser.DEFAULT);
        assertNotNull(parse);
        assertNull(parse.getMaximumSize());
        assertNull(parse.getTime());
        assertNull(parse.getTimeUnit());
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.cache.CacheConfigurationParser#parse(java.lang.String)}.
     */
    @Test
    public final void testParseHours() throws Exception {
    
    
        final ICacheConfiguration parse = new CacheConfigurationParser().parse("hours(1)");
        assertNotNull(parse);
        assertNull(parse.getMaximumSize());
        assertEquals(Integer.valueOf(1), parse.getTime());
        assertEquals(TimeUnit.HOURS, parse.getTimeUnit());
        
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.cache.CacheConfigurationParser#parse(java.lang.String)}.
     */
    @Test
    public final void testParseMax() throws Exception {
    
    
        final ICacheConfiguration parse = new CacheConfigurationParser().parse("max(1)");
        assertNotNull(parse);
        assertEquals(Integer.valueOf(1), parse.getMaximumSize());
        assertNull(parse.getTime());
        assertNull(parse.getTimeUnit());
        
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.cache.CacheConfigurationParser#parse(java.lang.String)}.
     */
    @Test
    public final void testParseMinutes() throws Exception {
    
    
        final ICacheConfiguration parse = new CacheConfigurationParser().parse("minutes(1)");
        assertNotNull(parse);
        assertNull(parse.getMaximumSize());
        assertEquals(Integer.valueOf(1), parse.getTime());
        assertEquals(TimeUnit.MINUTES, parse.getTimeUnit());
        
        
    }
}
