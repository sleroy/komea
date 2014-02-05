
package org.komea.product.wicket;



import java.text.MessageFormat;
import java.util.Date;

import org.joda.time.DateTime;



public class DateToJSDate
{
    
    
    public static String toJavaScript(final Date _date) {
    
    
        final DateTime dt = new DateTime(_date);
        // Year,month,day,hours,minutes,seconds,millisec
        return MessageFormat.format("Date.UTC({0},  {1}, {2}, {3}, {4})", dt.getYear(),
                dt.getMonthOfYear(), dt.getDayOfMonth(), dt.getHourOfDay(), dt.getMinuteOfHour(),
                dt.getSecondOfMinute());
        
        
    }
    
    
    public DateToJSDate() {
    
    
        super();
    }
    
}
