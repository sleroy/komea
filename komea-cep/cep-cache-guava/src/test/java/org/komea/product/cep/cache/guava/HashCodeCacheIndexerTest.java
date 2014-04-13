/**
 * 
 */

package org.komea.product.cep.cache.guava;



import org.junit.Test;
import org.komea.product.cep.cache.guava.HashCodeCacheIndexer;

import static org.junit.Assert.assertEquals;



/**
 * @author sleroy
 */
public class HashCodeCacheIndexerTest
{
    
    
    /**
     * Test method for {@link org.komea.product.cep.cache.guava.HashCodeCacheIndexer#getKey(java.io.Serializable)}.
     */
    @Test 
    public final void testGetKey() throws Exception {
    
    
        final HashCodeCacheIndexer<Integer> hashCodeCacheIndexer =
                new HashCodeCacheIndexer<Integer>();
        final Integer valueOf = Integer.valueOf(12);
        final Integer key = hashCodeCacheIndexer.getKey(valueOf);
        assertEquals(key, valueOf);
        
    }
    
}
