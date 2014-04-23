/**
 * 
 */

package org.komea.product.backend.service.kpi;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.cep.dynamicdata.IDynamicDataQuery;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CachedDynamicQueryTest
{
    
    
    @Mock
    private IDynamicDataQuery  dynamicDataQuery;
    
    
    @Mock
    private QueryCacheService  queryCacheService;
    @InjectMocks
    private CachedDynamicQuery cachedDynamicQuery;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.kpi.CachedDynamicQuery#getResult()}.
     */
    @Test
    public final void testGetResult() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
}
