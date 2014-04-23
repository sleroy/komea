/**
 * 
 */

package org.komea.product.backend.service.kpi;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.api.IDynamicDataQueryRegisterService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class QueryCacheCronTest
{
    
    
    @Mock
    private IDynamicDataQueryRegisterService queryAdministrator;
    @InjectMocks
    private QueryCacheCron                   queryCacheCron;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.kpi.QueryCacheCron#updateCacheOfDynamicQueries()}.
     */
    @Test
    public final void testUpdateCacheOfDynamicQueries() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
}
