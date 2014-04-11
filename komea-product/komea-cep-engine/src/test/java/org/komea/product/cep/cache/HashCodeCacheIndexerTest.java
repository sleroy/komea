/**
 * 
 */

package org.komea.product.cep.cache;



import org.junit.Test;

import static org.junit.Assert.assertEquals;



/**
 * @author sleroy
 */
public class HashCodeCacheIndexerTest
{
    
    
    /**
     * Test method for {@link org.komea.product.cep.cache.HashCodeCacheIndexer#getKey(java.io.Serializable)}.
     */
    @Test @Ignore
    public final void testGetKey() throws Exception {
    
    
        final HashCodeCacheIndexer<Integer> hashCodeCacheIndexer =
                new HashCodeCacheIndexer<Integer>();
        final Integer valueOf = Integer.valueOf(12);
        final Integer key = hashCodeCacheIndexer.getKey(valueOf);
        assertEquals(key, valueOf);
        
    }
    
}
