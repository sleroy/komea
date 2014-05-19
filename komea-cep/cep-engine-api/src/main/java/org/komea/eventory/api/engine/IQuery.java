
package org.komea.eventory.api.engine;



import org.komea.eventory.api.cache.BackupDelay;



/**
 * This interface defines a query
 * 
 * @author sleroy
 * @param <TRes>
 *            the type of result of this query.
 */
public interface IQuery<TRes>
{
    
    
    /**
     * Returns the backup delay.
     * 
     * @return the backup delay.
     */
    BackupDelay getBackupDelay();
    
    
    /**
     * Returns the result.
     * 
     * @return generic type
     */
    TRes getResult();
}
