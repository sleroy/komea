
package org.komea.eventory;



import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.eventory.QueryAdministrator;
import org.komea.eventory.api.bridge.IEventBridge;
import org.komea.eventory.api.engine.ICEPQuery;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;



@RunWith(MockitoJUnitRunner.class)
public class QueryAdministratorTest
{
    
    
    @Mock
    private IEventBridge eventListener;
    @InjectMocks
    private QueryAdministrator     queryAdministrator;
    
    
    
    @Test 
    public final void testExistQuery() throws Exception {
    
    
        queryAdministrator.registerQuery("truc", Mockito.mock(ICEPQuery.class));
        Assert.assertNotNull(queryAdministrator.getQuery("truc"));
        Assert.assertNotNull(queryAdministrator.getQueryNames().contains("truc"));
        Assert.assertTrue(queryAdministrator.existQuery("truc"));
        Assert.assertFalse(queryAdministrator.existQuery("Truc"));
    }
    
    
    @Test 
    public final void testGetQuery() throws Exception {
    
    
        queryAdministrator.registerQuery("truc", Mockito.mock(ICEPQuery.class));
        Assert.assertNotNull(queryAdministrator.getQuery("truc"));
        Assert.assertNull(queryAdministrator.getQuery("truc2"));
    }
    
    
    @Test 
    public final void testGetQueryNames() throws Exception {
    
    
        queryAdministrator.registerQuery("truc", Mockito.mock(ICEPQuery.class));
        Assert.assertNotNull(queryAdministrator.getQuery("truc"));
        Assert.assertNotNull(queryAdministrator.getQueryNames().contains("truc"));
        
    }
    
    
    @Test 
    public final void testQueryAdministrator() throws Exception {
    
    
        Assert.assertTrue(queryAdministrator.getQueryNames().isEmpty());
    }
    
    
    @Test 
    public final void testRegisterQuery() throws Exception {
    
    
        final ICEPQuery mock = Mockito.mock(ICEPQuery.class);
        final ICEPQuery mock2 = Mockito.mock(ICEPQuery.class);
        queryAdministrator.registerQuery("truc", mock);
        Assert.assertEquals(mock, queryAdministrator.getQuery("truc"));
        queryAdministrator.registerQuery("truc", mock2);
        Assert.assertEquals(mock2, queryAdministrator.getQuery("truc"));
        
    }
    
    
    @Test 
    public final void testRemoveQuery() throws Exception {
    
    
        queryAdministrator.registerQuery("truc", Mockito.mock(ICEPQuery.class));
        Assert.assertNotNull(queryAdministrator.getQuery("truc"));
        queryAdministrator.removeQuery("truc");
        Assert.assertNull(queryAdministrator.getQuery("truc"));
    }
    
}
