/**
 * 
 */

package org.komea.product.backend.criterias;



import java.util.Date;

import org.junit.Test;
import org.komea.product.database.model.Measure;

import static org.junit.Assert.assertEquals;



/**
 * @author sleroy
 */
public class DateComparatorTest
{
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.criterias.DateComparator#compare(org.komea.product.database.model.Measure, org.komea.product.database.model.Measure)}
     * .
     */
    @Test
    public final void testCompare() throws Exception {
    
    
        final DateComparator dateComparator = new DateComparator();
        final Measure measure = new Measure();
        measure.setDate(new Date());
        final Measure measure2 = new Measure();
        measure2.setDate(new Date());
        assertEquals(measure.getDate().compareTo(measure2.getDate()),
                dateComparator.compare(measure, measure2));
        
        
    }
    
}
