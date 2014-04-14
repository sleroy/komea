
package org.komea.eventory.filter;



import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.komea.eventory.filter.ElEventFilter;



public class ElEventFilterTest
{
    
    
    @Test
    public final void testELFormula2() throws Exception {
    
    
        final Event event = new Event();
        event.setMessage("truc");
        
        
        Assert.assertTrue(new ElEventFilter("message == 'truc'").isFiltered(event));
        Assert.assertFalse(new ElEventFilter("message != 'truc'").isFiltered(event));
    }
    
    
    @Test
    public final void testELFormulaFilterString() throws Exception {
    
    
        final Event event = new Event();
        
        event.setMessage("truc");
        Assert.assertTrue(new ElEventFilter("message == 'truc'").isFiltered(event));
        Assert.assertFalse(new ElEventFilter("message != 'truc'").isFiltered(event));
    }
    
    
    @Test
    public final void testELFormulaWithMap() throws Exception {
    
    
        final Event event = new Event();
        event.setMessage("truc");
        
        
        Assert.assertTrue(new ElEventFilter("message == 'truc'", Collections.EMPTY_MAP)
                .isFiltered(event));
        Assert.assertFalse(new ElEventFilter("message != 'truc'", Collections.EMPTY_MAP)
                .isFiltered(event));
        
    }
}
