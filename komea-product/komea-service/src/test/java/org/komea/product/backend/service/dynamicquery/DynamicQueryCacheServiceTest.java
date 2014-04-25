/**
 * 
 */

package org.komea.product.backend.service.dynamicquery;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.cep.dynamicdata.IDynamicDataQuery;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.mock;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class DynamicQueryCacheServiceTest
{
    
    
    @Mock
    private ICronRegistryService     cronRegistry;
    @InjectMocks
    private DynamicQueryCacheService dynamicQueryCacheService;
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.dynamicquery.DynamicQueryCacheService#addCacheOnDynamicQuery(org.komea.cep.dynamicdata.IDynamicDataQuery)}
     * .
     */
    @Test
    public final void testAddCacheOnDynamicQuery() throws Exception {
    
    
        final IDynamicDataQuery dynamicQuery =
                dynamicQueryCacheService.addCacheOnDynamicQuery("query",
                        mock(IDynamicDataQuery.class));
        assertTrue(dynamicQuery instanceof CachedDynamicQuery);
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.dynamicquery.DynamicQueryCacheService#QueryCacheService()}.
     */
    @Test
    public final void testQueryCacheService() throws Exception {
    
    
        // Cache should be empty
        assertTrue(dynamicQueryCacheService.getCache().size() == 0);
    }
    
}
