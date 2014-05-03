
package org.komea.product.cep.formula;



import org.junit.Assert;
import org.junit.Test;



public class EventGroupTest
{
    
    
    @Test
    public final void testAddEvent() throws Exception {
    
    
        final EventGroup eventGroup = new EventGroup();
        eventGroup.addEvent("truc");
        Assert.assertTrue(eventGroup.getEvents().contains("truc"));
    }
    
    
    @Test
    public final void testGetEvents() throws Exception {
    
    
        final EventGroup eventGroup = new EventGroup();
        Assert.assertTrue(eventGroup.getEvents().isEmpty());
        eventGroup.addEvent("truc");
        Assert.assertTrue(eventGroup.getEvents().contains("truc"));
    }
    
    
    @Test
    public void testGetFirstEvent() throws Exception {
    
    
        final EventGroup eventGroup = new EventGroup();
        Assert.assertTrue(eventGroup.getEvents().isEmpty());
        Assert.assertNull(eventGroup.getFirstEvent());
        eventGroup.addEvent("truc");
        Assert.assertEquals("truc", eventGroup.getFirstEvent());
        Assert.assertTrue(eventGroup.getEvents().contains("truc"));
        
    }
    
    
}
