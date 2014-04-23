/**
 * 
 */

package org.komea.product.backend.service.kpi;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class QueryCacheServiceTest
{
    
    
    @Mock
    private ICronRegistryService cronRegistry;
    @InjectMocks
    private QueryCacheService    queryCacheService;
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.kpi.QueryCacheService#addCacheOnDynamicQuery(org.komea.cep.dynamicdata.IDynamicDataQuery)}.
     */
    @Test
    public final void testAddCacheOnDynamicQuery() throws Exception {
    
    
        // TODO
        
        // TODO
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.kpi.QueryCacheService.CachedDynamicQuery#call()}.
     */
    @Test
    public void testCall() throws Exception {
    
    
        // TODO
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.kpi.QueryCacheService#QueryCacheService()}.
     */
    @Test
    public final void testQueryCacheService() throws Exception {
    
    
        // TODO
        
        // TODO
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
}
