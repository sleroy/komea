/**
 * 
 */

package org.komea.eventory.filter;



import org.junit.Test;
import org.komea.eventory.filter.ELCacheIndexer;

import static org.junit.Assert.assertEquals;



/**
 * @author sleroy
 */
public class ELCacheIndexerTest
{
    
    
    /**
     * Test method for {@link org.komea.eventory.filter.ELCacheIndexer#ELCacheIndexer(java.lang.String)}.
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
