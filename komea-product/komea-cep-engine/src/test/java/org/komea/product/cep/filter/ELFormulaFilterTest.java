
package org.komea.product.cep.filter;



import org.junit.Assert;
import org.junit.Test;
import org.komea.product.database.alert.Event;



public class ELFormulaFilterTest
{
    
    
    @Test @Ignore
    public final void testELFormula2() throws Exception {
    
    
        final Event event = new Event();
        event.setMessage("truc");
        
        
        Assert.assertTrue(new ELFormulaFilter("message == 'truc'").isFiltered(event));
        Assert.assertFalse(new ELFormulaFilter("message != 'truc'").isFiltered(event));
    }
    
    
    @Test @Ignore
    public final void testELFormulaFilterString() throws Exception {
    
    
        final Event event = new Event();
        
        event.setMessage("truc");
        Assert.assertTrue(new ELFormulaFilter("message == 'truc'").isFiltered(event));
        Assert.assertFalse(new ELFormulaFilter("message != 'truc'").isFiltered(event));
    }
    
}
