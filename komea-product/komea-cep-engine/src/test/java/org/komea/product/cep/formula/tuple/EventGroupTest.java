
package org.komea.product.cep.formula.tuple;



import org.junit.Assert;
import org.junit.Test;
import org.komea.product.cep.query.BasicUnitTests;



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
    public final void testToString() throws Exception {
    
    
        final BasicUnitTests basicUnitTests = new BasicUnitTests(EventGroup.class);
        basicUnitTests.testToString();
        basicUnitTests.testGetterSetter();
    }
    
}
