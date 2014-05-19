
package org.komea.product.model.timeserie;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TimecoordinateTest {
    
    @Before
    public void setUp() throws Exception {
    
    }
    //
    
    @Test
    public void testFindDayOfMonth_may() {
    
        TimeCoordinate time = new TimeCoordinate();
        time.setYear(2014);
        time.setMonth(5);
        time.setWeek(20);
        
        int day = time.findDayOfMonth();
        System.out.println("date of month = " + day);
        Assert.assertTrue("day must be >= 12", day >= 12);
        Assert.assertTrue("day must be >= 12", day <= 18);
    }
    
    @Test
    public void testFindDayOfMonth_avril() {
    
        TimeCoordinate time = new TimeCoordinate();
        time.setYear(2014);
        time.setMonth(4);
        time.setWeek(17);
        
        int day = time.findDayOfMonth();
        System.out.println("date of month = " + day);
        Assert.assertTrue("day must be >= 21", day >= 21);
        Assert.assertTrue("day must be >= 27", day <= 27);
    }
    
    @Test
    public void testFindDayOfMonth_end_avril() {
    
        TimeCoordinate time = new TimeCoordinate();
        time.setYear(2014);
        time.setMonth(4);
        time.setWeek(18);
        
        int day = time.findDayOfMonth();
        System.out.println("date of month = " + day);
        Assert.assertTrue("day must be >= 28", day >= 28);
        Assert.assertTrue("day must be >= 30", day <= 30);
    }
    
}
