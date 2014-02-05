
package org.komea.product.wicket;



import java.util.Date;

import org.joda.time.DateTime;



public class DateToJSDate
{
    
    
    public static String toJavaScript(final Date _date) {
    
    
        final DateTime dt = new DateTime(_date);
        // Year,month,day,hours,minutes,seconds,millisec
        return String
                .format("Date.UTC(%d,  %d, %d, %d, %d)", dt.getYear(), dt.getMonthOfYear(),
                        dt.getDayOfMonth(), dt.getHourOfDay(), dt.getMinuteOfHour(),
                        dt.getSecondOfMinute());
        
        
    }
    
    
    public DateToJSDate() {
    
    
        super();
    }
    
}
