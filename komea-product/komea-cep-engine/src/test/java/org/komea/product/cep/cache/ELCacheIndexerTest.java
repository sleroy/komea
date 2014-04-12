/**
 * 
 */

package org.komea.product.cep.cache;



import org.junit.Test;
import org.komea.product.database.alert.Event;

import static org.junit.Assert.assertEquals;



/**
 * @author sleroy
 */
public class ELCacheIndexerTest
{
    
    
    /**
     * Test method for {@link org.komea.product.cep.cache.ELCacheIndexer#ELCacheIndexer(java.lang.String)}.
     */
    @Test
    public final void testELCacheIndexer() throws Exception {
    
    
        final ELCacheIndexer elCacheIndexer = new ELCacheIndexer("message");
        final Event event = new Event();
        event.setMessage("a");
        
        final Event event2 = new Event();
        event2.setMessage("b");
        assertEquals("b", elCacheIndexer.getKey(event2));
        assertEquals("a", elCacheIndexer.getKey(event));
    }
    
    
}
