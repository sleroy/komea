/**
 * 
 */

package org.komea.product.backend.service.dynamicquery;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.cep.dynamicdata.IDynamicDataQuery;
import org.komea.eventory.api.formula.ICEPResult;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.cache.Cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class CachedDynamicQueryTest
{
    
    
    @InjectMocks
    private CachedDynamicQuery       cachedDynamicQuery;
    
    
    @Mock
    private IDynamicDataQuery        dynamicDataQuery;
    @Mock
    private DynamicQueryCacheService dynamicQueryCacheService;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.dynamicquery.CachedDynamicQuery#getResult()}.
     */
    @Test
    public final void testGetResultWithCacheValue() throws Exception {
    
    
        final Cache cache = mock(Cache.class);
        when(dynamicQueryCacheService.getCache()).thenReturn(cache);
        final ICEPResult icepResult = mock(ICEPResult.class);
        when(cache.getIfPresent(Matchers.any(Object.class))).thenReturn(icepResult);
        final ICEPResult result = cachedDynamicQuery.getResult();
        verify(dynamicDataQuery, never()).getResult();
        assertEquals(icepResult, result);
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.dynamicquery.CachedDynamicQuery#getResult()}.
     */
    @Test
    public final void testGetResultWithoutCacheValueButOriginalValue() throws Exception {
    
    
        final Cache cache = mock(Cache.class);
        when(dynamicQueryCacheService.getCache()).thenReturn(cache);
        final ICEPResult icepResult = mock(ICEPResult.class);
        when(cache.getIfPresent(Matchers.any(Object.class))).thenReturn(null);
        when(dynamicDataQuery.getResult()).thenReturn(icepResult);
        final ICEPResult result = cachedDynamicQuery.getResult();
        verify(dynamicDataQuery, times(1)).getResult();
        assertEquals(icepResult, result);
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.dynamicquery.CachedDynamicQuery#getResult()}.
     */
    @Test
    public final void testGetResultWithoutCacheValueNoOriginalValue() throws Exception {
    
    
        final Cache cache = mock(Cache.class);
        when(dynamicQueryCacheService.getCache()).thenReturn(cache);
        when(cache.getIfPresent(Matchers.any(Object.class))).thenReturn(null);
        final ICEPResult result = cachedDynamicQuery.getResult();
        verify(dynamicDataQuery, times(1)).getResult();
        assertNotNull(result);
        
    }
}
