/**
 * 
 */

package org.komea.product.backend.service.dynamicquery;



import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.cep.dynamicdata.IDynamicDataQuery;
import org.komea.product.backend.api.IDynamicDataQueryRegisterService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



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
     * Test method for {@link org.komea.product.backend.service.dynamicquery.QueryCacheCron#updateCacheOfDynamicQueries()}.
     */
    @Test
    public final void testUpdateCacheOfDynamicQueries() throws Exception {
    
    
        final List<IDynamicDataQuery> dataQueries = new ArrayList<IDynamicDataQuery>();
        final IDynamicDataQuery queryMock = mock(IDynamicDataQuery.class);
        dataQueries.add(queryMock);
        when(queryAdministrator.getQueriesIterator()).thenReturn(dataQueries.iterator());
        queryCacheCron.updateCacheOfDynamicQueries();
        verify(queryMock, times(1)).getResult();
    }
    
}
