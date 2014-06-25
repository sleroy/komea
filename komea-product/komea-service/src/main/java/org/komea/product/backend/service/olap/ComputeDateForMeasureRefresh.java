/**
 *
 */

package org.komea.product.backend.service.olap;



import org.joda.time.DateTime;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.product.database.model.Measure;



/**
 * @author sleroy
 */
public class ComputeDateForMeasureRefresh
{
    
    
    private final BackupDelay backupDelay;
    private final Measure     measure;
    private final String      sprint;
    
    
    
    public ComputeDateForMeasureRefresh(
            final BackupDelay _backupDelay,
            final Measure _measure,
            final String _sprint) {
    
    
        backupDelay = _backupDelay;
        measure = _measure;
        sprint = _sprint;
        
        
    }
    
    
    public Measure initializeMeasureWithDate(final DateTime _dateTime) {
    
    
        /**
         * Set default fields
         */
        measure.setDateTime(_dateTime);
        measure.setSprint(sprint);

        /**
         * Nullify fields useless
         */
        switch (backupDelay) {
            case MONTH:
                measure.setWeek(1);
            case WEEK:
                measure.setDay(1);
            case DAY:
                measure.setHour(0);
            case HOUR:
                break;
            default:
                throw new UnsupportedOperationException();
        }
        
        return measure;
    }
    
}
