/**
 * 
 */

package org.komea.eventory.filter;



import org.junit.Assert;
import org.junit.Test;
import org.komea.eventory.filter.NoEventFilter;



/**
 * @author sleroy
 */
public class NoEventFilterTest
{
    
    
    /**
     * Test method for {@link org.komea.eventory.filter.NoEventFilter#isFiltered(java.io.Serializable)}.
     */
    @Test 
    public final void testIsFiltered() throws Exception {
    
    
        final NoEventFilter noEventFilter = new NoEventFilter();
        Assert.assertTrue(noEventFilter.isFiltered("string"));
        Assert.assertTrue(noEventFilter.isFiltered(1));
    }
    
}
