
package org.komea.product.backend.exceptions;



import org.quartz.SchedulerException;



/**
 */
public class CronRuntimeException extends RuntimeException
{
    
    
    /**
     * Constructor for CronRuntimeException.
     * @param _e SchedulerException
     */
    public CronRuntimeException(final SchedulerException _e) {
    
    
        super("Error inside the cron registry, some action on a task has failed ", _e);
    }
    
    
    /**
     * Constructor for CronRuntimeException.
     * @param _message String
     * @param _e SchedulerException
     */
    public CronRuntimeException(final String _message, final SchedulerException _e) {
    
    
        super(_message, _e);
    }
}
