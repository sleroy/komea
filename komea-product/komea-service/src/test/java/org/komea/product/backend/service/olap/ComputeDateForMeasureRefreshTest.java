/**
 *
 */

package org.komea.product.backend.service.olap;



import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.product.database.model.Measure;

import static org.junit.Assert.assertEquals;



/**
 * @author sleroy
 */
public class ComputeDateForMeasureRefreshTest
{


    private static final int DAY_OF_MONTH   = 01;
    private static final int HOUR_OF_DAY    = 1;
    private static final int MINUTE_OF_HOUR = 10;
    private static final int MONTH_OF_YEAR  = 12;
    private static final int YEAR           = 2014;

    private DateTime         dateTime;
    
    
    
    @Before
    public void before() {
    
    
        dateTime = new DateTime(YEAR, MONTH_OF_YEAR, DAY_OF_MONTH, HOUR_OF_DAY, MINUTE_OF_HOUR);
    }


    /**
     * Test method for
     * {@link org.komea.product.backend.service.olap.ComputeDateForMeasureRefresh#initializeMeasureWithDate(org.joda.time.DateTime)}.
     */
    @Test
    public final void testMeasureDateRefreshedByDay() throws Exception {


        final Measure measure = new Measure();
        final ComputeDateForMeasureRefresh computer =
                new ComputeDateForMeasureRefresh(BackupDelay.DAY, measure, "");
        computer.initializeMeasureWithDate(dateTime);
        assertEquals(Integer.valueOf(YEAR), measure.getYear());
        assertEquals(Integer.valueOf(MONTH_OF_YEAR), measure.getMonth());
        assertEquals(Integer.valueOf(DAY_OF_MONTH), measure.getDay());
        assertEquals(Integer.valueOf(0), measure.getHour());
        
        
    }


    /**
     * Test method for
     * {@link org.komea.product.backend.service.olap.ComputeDateForMeasureRefresh#initializeMeasureWithDate(org.joda.time.DateTime)}.
     */
    @Test
    public final void testMeasureDateRefreshedByHour() throws Exception {


        final Measure measure = new Measure();
        final ComputeDateForMeasureRefresh computer =
                new ComputeDateForMeasureRefresh(BackupDelay.HOUR, measure, "");
        computer.initializeMeasureWithDate(dateTime);
        assertEquals(Integer.valueOf(YEAR), measure.getYear());
        assertEquals(Integer.valueOf(MONTH_OF_YEAR), measure.getMonth());
        assertEquals(Integer.valueOf(DAY_OF_MONTH), measure.getDay());
        assertEquals(Integer.valueOf(HOUR_OF_DAY), measure.getHour());
        
        
    }


    /**
     * Test method for
     * {@link org.komea.product.backend.service.olap.ComputeDateForMeasureRefresh#initializeMeasureWithDate(org.joda.time.DateTime)}.
     */
    @Test
    public final void testMeasureDateRefreshedByMonth() throws Exception {


        final Measure measure = new Measure();
        final ComputeDateForMeasureRefresh computer =
                new ComputeDateForMeasureRefresh(BackupDelay.MONTH, measure, "");
        computer.initializeMeasureWithDate(dateTime);
        assertEquals(Integer.valueOf(YEAR), measure.getYear());
        assertEquals(Integer.valueOf(MONTH_OF_YEAR), measure.getMonth());
        assertEquals(Integer.valueOf(1), measure.getDay());
        assertEquals(Integer.valueOf(0), measure.getHour());
        
        
    }


    /**
     * Test method for
     * {@link org.komea.product.backend.service.olap.ComputeDateForMeasureRefresh#initializeMeasureWithDate(org.joda.time.DateTime)}.
     */
    @Test
    public final void testMeasureDateRefreshedByWeek() throws Exception {


        final Measure measure = new Measure();
        final ComputeDateForMeasureRefresh computer =
                new ComputeDateForMeasureRefresh(BackupDelay.WEEK, measure, "");
        computer.initializeMeasureWithDate(dateTime);
        assertEquals(Integer.valueOf(YEAR), measure.getYear());
        assertEquals(Integer.valueOf(MONTH_OF_YEAR), measure.getMonth());
        assertEquals(Integer.valueOf(1), measure.getDay());
        assertEquals(Integer.valueOf(0), measure.getHour());
        
        
    }

}
