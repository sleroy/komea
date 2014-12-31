package org.komea.experimental;

import java.util.Date;

import org.joda.time.DateTime;
import org.komea.experimental.model.AnalyzedApplication;


public class QueriesUtils
{
    public static String format(final Date date) {
        
        DateTime dtime = new DateTime(date);
        
        return dtime.toString("yyyy-MM-dd");
    }
   
    
}
