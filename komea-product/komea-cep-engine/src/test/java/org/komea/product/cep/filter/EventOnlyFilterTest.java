
package org.komea.product.cep.filter;



import org.junit.Assert;
import org.junit.Test;
import org.komea.product.database.alert.IEvent;
import org.mockito.Mockito;



public class EventOnlyFilterTest
{
    
    
    @Test
    public final void testIsFiltered() throws Exception {
    
    
        final EventOnlyFilter eventOnlyFilter = new EventOnlyFilter();
        Assert.assertFalse(eventOnlyFilter.isFiltered("truc"));
        Assert.assertTrue(eventOnlyFilter.isFiltered(Mockito.mock(IEvent.class)));
    }
    
}
