/**
 *
 */

package org.komea.eventory.bridge;



import org.junit.Assert;
import org.junit.Test;
import org.komea.eventory.api.engine.ICEPQuery;
import org.mockito.Mockito;



/**
 * This class defiens a query listener.
 *
 * @author sleroy
 */
public class MemoryBridgeTest
{


    /**
     * Test method for {@link org.komea.eventory.bridge.MemoryBridge#getQuery(java.lang.String)}.
     */
    @Test
    public final void testGetQuery() throws Exception {


        final MemoryBridge cepQueryListener = new MemoryBridge();
        final ICEPQuery mock = Mockito.mock(ICEPQuery.class);
        cepQueryListener.registerQuery("test", mock);
        Assert.assertEquals(mock, cepQueryListener.getQuery("test"));
    }


    /**
     * Test method for {@link org.komea.eventory.bridge.MemoryBridge#notify(java.io.Serializable)}.
     */
    @Test
    public final void testNotify() throws Exception {


        final MemoryBridge cepQueryListener = new MemoryBridge();
        final ICEPQuery mock = Mockito.mock(ICEPQuery.class);
        cepQueryListener.registerQuery("test", mock);
        cepQueryListener.notify("truc");
        Mockito.verify(mock, Mockito.atLeastOnce()).notifyEvent("truc");
    }


    /**
     * Test method for
     * {@link org.komea.eventory.bridge.MemoryBridge#registerQuery(java.lang.String, org.komea.eventory.api.engine.api.ICEPQuery)}.
     */
    @Test
    public final void testRegisterQuery() throws Exception {


        final MemoryBridge cepQueryListener = new MemoryBridge();
        final ICEPQuery mock = Mockito.mock(ICEPQuery.class);
        cepQueryListener.registerQuery("test", mock);
        Assert.assertEquals(mock, cepQueryListener.getQuery("test"));
        cepQueryListener.registerQuery("test", mock);
        Assert.assertEquals(mock, cepQueryListener.getQuery("test"));
    }


    /**
     * Test method for {@link org.komea.eventory.bridge.MemoryBridge#removeQuery(java.lang.String)}.
     */
    @Test
    public final void testRemoveQuery() throws Exception {


        final MemoryBridge cepQueryListener = new MemoryBridge();
        final ICEPQuery mock = Mockito.mock(ICEPQuery.class);
        cepQueryListener.registerQuery("test", mock);
        Assert.assertEquals(mock, cepQueryListener.getQuery("test"));
        cepQueryListener.removeQuery("test");
        Assert.assertNull(cepQueryListener.getQuery("test"));
    }
}
