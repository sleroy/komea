/**
 * 
 */

package org.komea.product.backend.service.dynamicquery;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.cep.dynamicdata.IDynamicDataQuery;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class CachedDynamicQueryTest
{
    
    
    @InjectMocks
    private CachedDynamicQuery cachedDynamicQuery;
    
    
    @Mock
    private IDynamicDataQuery  dynamicDataQuery;
    @Mock
    private QueryCacheService  queryCacheService;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.dynamicquery.CachedDynamicQuery#getResult()}.
     */
    @Test
    public final void testGetResult() throws Exception {
    
    
        // TODO
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
}
