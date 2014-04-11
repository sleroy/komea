/**
 * 
 */

package org.komea.product.cep.filter;



import org.junit.Assert;
import org.junit.Test;



/**
 * @author sleroy
 */
public class NoEventFilterTest
{
    
    
    /**
     * Test method for {@link org.komea.product.cep.filter.NoEventFilter#isFiltered(java.io.Serializable)}.
     */
    @Test @Ignore
    public final void testIsFiltered() throws Exception {
    
    
        final NoEventFilter noEventFilter = new NoEventFilter();
        Assert.assertTrue(noEventFilter.isFiltered("string"));
        Assert.assertTrue(noEventFilter.isFiltered(1));
    }
    
}
