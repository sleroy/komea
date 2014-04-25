/**
 * 
 */

package org.komea.product.backend.service.dynamicquery;



import org.junit.Test;
import org.komea.cep.dynamicdata.IDynamicDataQuery;
import org.komea.product.backend.service.dynamicquery.DynamicQueryRegisterService;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.mock;



/**
 * @author sleroy
 */
public class DynamicQueryRegisterServiceTest
{
    
    
    /**
     * 
     */
    private static final String ENGINE_KEY = "engineKey";
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.dynamicquery.DynamicQueryRegisterService#existQuery(java.lang.String)}.
     */
    @Test
    public final void testExistQuery() throws Exception {
    
    
        final DynamicQueryRegisterService dynamicQueryRegisterService =
                new DynamicQueryRegisterService();
        assertFalse(dynamicQueryRegisterService.existQuery(ENGINE_KEY));
        dynamicQueryRegisterService.registerQuery(ENGINE_KEY, mock(IDynamicDataQuery.class));
        assertTrue(dynamicQueryRegisterService.existQuery(ENGINE_KEY));
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.dynamicquery.DynamicQueryRegisterService#getQueriesIterator()}.
     */
    @Test
    public final void testGetQueriesIterator() throws Exception {
    
    
        final DynamicQueryRegisterService dynamicQueryRegisterService =
                new DynamicQueryRegisterService();
        dynamicQueryRegisterService.registerQuery(ENGINE_KEY, mock(IDynamicDataQuery.class));
        assertTrue(dynamicQueryRegisterService.getQueriesIterator().hasNext());
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.dynamicquery.DynamicQueryRegisterService#getQuery(java.lang.String)}.
     */
    @Test
    public final void testGetQuery() throws Exception {
    
    
        final DynamicQueryRegisterService dynamicQueryRegisterService =
                new DynamicQueryRegisterService();
        assertFalse(dynamicQueryRegisterService.existQuery(ENGINE_KEY));
        dynamicQueryRegisterService.registerQuery(ENGINE_KEY, mock(IDynamicDataQuery.class));
        assertNotNull(dynamicQueryRegisterService.getQuery(ENGINE_KEY));
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.dynamicquery.DynamicQueryRegisterService#getQueryNames()}.
     */
    @Test
    public final void testGetQueryNames() throws Exception {
    
    
        final DynamicQueryRegisterService dynamicQueryRegisterService =
                new DynamicQueryRegisterService();
        dynamicQueryRegisterService.registerQuery(ENGINE_KEY, mock(IDynamicDataQuery.class));
        assertTrue(dynamicQueryRegisterService.getQueryNames().contains(ENGINE_KEY));
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.dynamicquery.DynamicQueryRegisterService#registerQuery(java.lang.String, org.komea.cep.dynamicdata.IDynamicDataQuery)}
     * .
     */
    @Test
    public final void testRegisterQuery() throws Exception {
    
    
        final DynamicQueryRegisterService dynamicQueryRegisterService =
                new DynamicQueryRegisterService();
        dynamicQueryRegisterService.registerQuery(ENGINE_KEY, mock(IDynamicDataQuery.class));
        dynamicQueryRegisterService.registerQuery(ENGINE_KEY, mock(IDynamicDataQuery.class));
        assertTrue(dynamicQueryRegisterService.existQuery(ENGINE_KEY));
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.dynamicquery.DynamicQueryRegisterService#removeQuery(java.lang.String)}.
     */
    @Test
    public final void testRemoveQuery() throws Exception {
    
    
        final DynamicQueryRegisterService dynamicQueryRegisterService =
                new DynamicQueryRegisterService();
        dynamicQueryRegisterService.registerQuery(ENGINE_KEY, mock(IDynamicDataQuery.class));
        assertTrue(dynamicQueryRegisterService.existQuery(ENGINE_KEY));
        dynamicQueryRegisterService.removeQuery(ENGINE_KEY);
        assertFalse(dynamicQueryRegisterService.existQuery(ENGINE_KEY));
    }
    
}
