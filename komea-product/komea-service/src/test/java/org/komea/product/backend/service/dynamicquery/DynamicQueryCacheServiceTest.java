/**
 * 
 */

package org.komea.product.backend.service.dynamicquery;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.cep.dynamicdata.IDynamicDataQuery;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.quartz.JobDataMap;

import static org.mockito.Matchers.anyString;

import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class DynamicQueryCacheServiceTest
{
    
    
    @Mock
    private ICronRegistryService cronRegistry;
    @InjectMocks
    private DynamicQueryCacheService    dynamicQueryCacheService;
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.dynamicquery.DynamicQueryCacheService#addCacheOnDynamicQuery(org.komea.cep.dynamicdata.IDynamicDataQuery)}
     * .
     */
    @Test
    public final void testAddCacheOnDynamicQuery() throws Exception {
    
    
        final IDynamicDataQuery dynamicQuery =
                dynamicQueryCacheService.addCacheOnDynamicQuery(mock(IDynamicDataQuery.class));
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
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.dynamicquery.DynamicQueryCacheService#registerCron()}.
     */
    @Test
    public void testRegisterCron() throws Exception {
    
    
        dynamicQueryCacheService.registerCron();
        verify(dynamicQueryCacheService.getCronRegistry(), times(1)).existCron(DynamicQueryCacheService.CRON_KEY);
        verify(dynamicQueryCacheService.getCronRegistry(), times(1)).registerCronTask(
                Matchers.eq(DynamicQueryCacheService.CRON_KEY), anyString(), Matchers.any(Class.class),
                Matchers.any(JobDataMap.class));
    }
}
