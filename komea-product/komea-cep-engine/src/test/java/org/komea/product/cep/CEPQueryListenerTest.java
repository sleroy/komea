/**
 * 
 */

package org.komea.product.cep;



import org.junit.Assert;
import org.junit.Test;
import org.komea.product.cep.api.ICEPQuery;
import org.mockito.Mockito;



/**
 * This class defiens a query listener.
 * 
 * @author sleroy
 */
public class CEPQueryListenerTest
{
    
    
    /**
     * Test method for {@link org.komea.product.cep.CEPQueryListener#getQuery(java.lang.String)}.
     */
    @Test
    public final void testGetQuery() throws Exception {
    
    
        final CEPQueryListener cepQueryListener = new CEPQueryListener();
        final ICEPQuery mock = Mockito.mock(ICEPQuery.class);
        cepQueryListener.registerQuery("test", mock);
        Assert.assertEquals(mock, cepQueryListener.getQuery("test"));
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.CEPQueryListener#notify(java.io.Serializable)}.
     */
    @Test
    public final void testNotify() throws Exception {
    
    
        final CEPQueryListener cepQueryListener = new CEPQueryListener();
        final ICEPQuery mock = Mockito.mock(ICEPQuery.class);
        cepQueryListener.registerQuery("test", mock);
        cepQueryListener.notify("truc");
        Mockito.verify(mock, Mockito.atLeastOnce()).notifyEvent("truc");
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.CEPQueryListener#registerQuery(java.lang.String, org.komea.product.cep.api.ICEPQuery)}.
     */
    @Test
    public final void testRegisterQuery() throws Exception {
    
    
        final CEPQueryListener cepQueryListener = new CEPQueryListener();
        final ICEPQuery mock = Mockito.mock(ICEPQuery.class);
        cepQueryListener.registerQuery("test", mock);
        Assert.assertEquals(mock, cepQueryListener.getQuery("test"));
        cepQueryListener.registerQuery("test", mock);
        Assert.assertEquals(mock, cepQueryListener.getQuery("test"));
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.CEPQueryListener#removeQuery(java.lang.String)}.
     */
    @Test
    public final void testRemoveQuery() throws Exception {
    
    
        final CEPQueryListener cepQueryListener = new CEPQueryListener();
        final ICEPQuery mock = Mockito.mock(ICEPQuery.class);
        cepQueryListener.registerQuery("test", mock);
        Assert.assertEquals(mock, cepQueryListener.getQuery("test"));
        cepQueryListener.removeQuery("test");
        Assert.assertNull(cepQueryListener.getQuery("test"));
    }
    
}
