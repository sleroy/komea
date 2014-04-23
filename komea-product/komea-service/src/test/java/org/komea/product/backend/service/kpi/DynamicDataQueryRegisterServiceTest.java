/**
 * 
 */

package org.komea.product.backend.service.kpi;



import org.junit.Test;
import org.komea.cep.dynamicdata.IDynamicDataQuery;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.mock;



/**
 * @author sleroy
 */
public class DynamicDataQueryRegisterServiceTest
{
    
    
    /**
     * 
     */
    private static final String ENGINE_KEY = "engineKey";
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.kpi.DynamicDataQueryRegisterService#existQuery(java.lang.String)}.
     */
    @Test
    public final void testExistQuery() throws Exception {
    
    
        final DynamicDataQueryRegisterService dynamicDataQueryRegisterService =
                new DynamicDataQueryRegisterService();
        assertFalse(dynamicDataQueryRegisterService.existQuery(ENGINE_KEY));
        dynamicDataQueryRegisterService.registerQuery(ENGINE_KEY, mock(IDynamicDataQuery.class));
        assertTrue(dynamicDataQueryRegisterService.existQuery(ENGINE_KEY));
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.kpi.DynamicDataQueryRegisterService#getQueriesIterator()}.
     */
    @Test
    public final void testGetQueriesIterator() throws Exception {
    
    
        final DynamicDataQueryRegisterService dynamicDataQueryRegisterService =
                new DynamicDataQueryRegisterService();
        dynamicDataQueryRegisterService.registerQuery(ENGINE_KEY, mock(IDynamicDataQuery.class));
        assertTrue(dynamicDataQueryRegisterService.getQueriesIterator().hasNext());
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.kpi.DynamicDataQueryRegisterService#getQuery(java.lang.String)}.
     */
    @Test
    public final void testGetQuery() throws Exception {
    
    
        final DynamicDataQueryRegisterService dynamicDataQueryRegisterService =
                new DynamicDataQueryRegisterService();
        assertFalse(dynamicDataQueryRegisterService.existQuery(ENGINE_KEY));
        dynamicDataQueryRegisterService.registerQuery(ENGINE_KEY, mock(IDynamicDataQuery.class));
        assertNotNull(dynamicDataQueryRegisterService.getQuery(ENGINE_KEY));
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.kpi.DynamicDataQueryRegisterService#getQueryNames()}.
     */
    @Test
    public final void testGetQueryNames() throws Exception {
    
    
        final DynamicDataQueryRegisterService dynamicDataQueryRegisterService =
                new DynamicDataQueryRegisterService();
        dynamicDataQueryRegisterService.registerQuery(ENGINE_KEY, mock(IDynamicDataQuery.class));
        assertTrue(dynamicDataQueryRegisterService.getQueryNames().contains(ENGINE_KEY));
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.kpi.DynamicDataQueryRegisterService#registerQuery(java.lang.String, org.komea.cep.dynamicdata.IDynamicDataQuery)}
     * .
     */
    @Test
    public final void testRegisterQuery() throws Exception {
    
    
        final DynamicDataQueryRegisterService dynamicDataQueryRegisterService =
                new DynamicDataQueryRegisterService();
        dynamicDataQueryRegisterService.registerQuery(ENGINE_KEY, mock(IDynamicDataQuery.class));
        dynamicDataQueryRegisterService.registerQuery(ENGINE_KEY, mock(IDynamicDataQuery.class));
        assertTrue(dynamicDataQueryRegisterService.existQuery(ENGINE_KEY));
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.kpi.DynamicDataQueryRegisterService#removeQuery(java.lang.String)}.
     */
    @Test
    public final void testRemoveQuery() throws Exception {
    
    
        final DynamicDataQueryRegisterService dynamicDataQueryRegisterService =
                new DynamicDataQueryRegisterService();
        dynamicDataQueryRegisterService.registerQuery(ENGINE_KEY, mock(IDynamicDataQuery.class));
        assertTrue(dynamicDataQueryRegisterService.existQuery(ENGINE_KEY));
        dynamicDataQueryRegisterService.removeQuery(ENGINE_KEY);
        assertFalse(dynamicDataQueryRegisterService.existQuery(ENGINE_KEY));
    }
    
}
