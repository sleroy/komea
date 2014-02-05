
package org.komea.product.backend.exceptions;



import org.quartz.SchedulerException;



public class CronRuntimeException extends RuntimeException
{
    
    
    public CronRuntimeException(final SchedulerException _e) {
    
    
        super("Error inside the cron registry, some action on a task has failed ", _e);
    }
    
    
    public CronRuntimeException(final String _message, final SchedulerException _e) {
    
    
        super(_message, _e);
    }
}
