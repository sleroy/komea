/**
 * 
 */

package org.komea.product.backend.groovy;



import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.IDynamicDataQuery;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.EntityType;



/**
 * @author sleroy
 */
public abstract class AbstractDynamicQuery extends AbstractGroovyQuery implements
        IDynamicDataQuery<KpiResult>
{
    
    
    private final BackupDelay delay;
    
    
    
    public AbstractDynamicQuery(final EntityType _entityType, final BackupDelay _delay) {
    
    
        super(_entityType);
        delay = _delay;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.engine.IQuery#getBackupDelay()
     */
    @Override
    public BackupDelay getBackupDelay() {
    
    
        return delay;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.engine.IQuery#getResult()
     */
    @Override
    public abstract KpiResult getResult();
    
}
