/**
 * 
 */

package org.komea.product.backend.service.dynamicquery;



import org.junit.Test;
import org.komea.eventory.api.formula.ICEPResult;
import org.komea.product.backend.api.IDynamicDataQueryRegisterService;

import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.mock;



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
    
    
        final IDynamicDataQueryRegisterService mock = mock(IDynamicDataQueryRegisterService.class);
        final DynamicQueryCacheLoader dynamicQueryCacheLoader = new DynamicQueryCacheLoader(mock);
        final ICEPResult load = dynamicQueryCacheLoader.load("test");
        assertTrue(load.asMap().getTable().isEmpty());
    }
}
