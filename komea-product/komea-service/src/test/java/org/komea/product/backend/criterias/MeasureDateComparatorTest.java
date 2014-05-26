/**
 * 
 */

package org.komea.product.backend.criterias;



import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.komea.product.database.model.Measure;



/**
 * @author sleroy
 */
public class MeasureDateComparatorTest
{
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.criterias.MeasureDateComparator#compare(org.komea.product.database.model.Measure, org.komea.product.database.model.Measure)}
     * .
     */
    @Test
    public final void testCompare() throws Exception {
    
    
        final MeasureDateComparator measureDateComparator = new MeasureDateComparator();
        final Measure measure = new Measure();
        Date date = new Date();
        measure.setDate(date);
        final Measure measure2 = new Measure();
        measure2.setDate(date);
        assertEquals(measure.getDate().compareTo(measure2.getDate()),
                measureDateComparator.compare(measure, measure2));
        
        
    }
    
}
