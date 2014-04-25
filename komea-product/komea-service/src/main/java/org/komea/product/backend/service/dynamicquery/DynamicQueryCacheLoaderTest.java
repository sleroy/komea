/**
 * 
 */

package org.komea.product.backend.service.dynamicquery;



import org.junit.Test;
import org.komea.eventory.api.formula.ICEPResult;
import org.komea.product.backend.service.dynamicquery.DynamicQueryCacheLoader;

import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */
public class DynamicQueryCacheLoaderTest
{
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.dynamicquery.DynamicQueryCacheLoader#load(java.lang.String)}.
     */
    @Test
    public final void testLoad() throws Exception {
    
    
        final DynamicQueryCacheLoader dynamicQueryCacheLoader = new DynamicQueryCacheLoader();
        final ICEPResult load = dynamicQueryCacheLoader.load("test");
        assertTrue(load.asMap().getTable().isEmpty());
    }
}
