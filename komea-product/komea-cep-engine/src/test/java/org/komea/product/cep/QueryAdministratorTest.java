
package org.komea.product.cep;



import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.cep.api.ICEPQuery;
import org.komea.product.cep.api.ICEPQueryEventListener;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;



@RunWith(MockitoJUnitRunner.class)
public class QueryAdministratorTest
{
    
    
    @Mock
    private ICEPQueryEventListener eventListener;
    @InjectMocks
    private QueryAdministrator     queryAdministrator;
    
    
    
    @Test @Ignore
    public final void testExistQuery() throws Exception {
    
    
        queryAdministrator.registerQuery("truc", Mockito.mock(ICEPQuery.class));
        Assert.assertNotNull(queryAdministrator.getQuery("truc"));
        Assert.assertNotNull(queryAdministrator.getQueryNames().contains("truc"));
        Assert.assertTrue(queryAdministrator.existQuery("truc"));
        Assert.assertFalse(queryAdministrator.existQuery("Truc"));
    }
    
    
    @Test @Ignore
    public final void testGetQuery() throws Exception {
    
    
        queryAdministrator.registerQuery("truc", Mockito.mock(ICEPQuery.class));
        Assert.assertNotNull(queryAdministrator.getQuery("truc"));
        Assert.assertNull(queryAdministrator.getQuery("truc2"));
    }
    
    
    @Test @Ignore
    public final void testGetQueryNames() throws Exception {
    
    
        queryAdministrator.registerQuery("truc", Mockito.mock(ICEPQuery.class));
        Assert.assertNotNull(queryAdministrator.getQuery("truc"));
        Assert.assertNotNull(queryAdministrator.getQueryNames().contains("truc"));
        
    }
    
    
    @Test @Ignore
    public final void testQueryAdministrator() throws Exception {
    
    
        Assert.assertTrue(queryAdministrator.getQueryNames().isEmpty());
    }
    
    
    @Test @Ignore
    public final void testRegisterQuery() throws Exception {
    
    
        final ICEPQuery mock = Mockito.mock(ICEPQuery.class);
        final ICEPQuery mock2 = Mockito.mock(ICEPQuery.class);
        queryAdministrator.registerQuery("truc", mock);
        Assert.assertEquals(mock, queryAdministrator.getQuery("truc"));
        queryAdministrator.registerQuery("truc", mock2);
        Assert.assertEquals(mock2, queryAdministrator.getQuery("truc"));
        
    }
    
    
    @Test @Ignore
    public final void testRemoveQuery() throws Exception {
    
    
        queryAdministrator.registerQuery("truc", Mockito.mock(ICEPQuery.class));
        Assert.assertNotNull(queryAdministrator.getQuery("truc"));
        queryAdministrator.removeQuery("truc");
        Assert.assertNull(queryAdministrator.getQuery("truc"));
    }
    
}
