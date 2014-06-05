/**
 * 
 */

package org.komea.product.backend.groovy;



import org.apache.commons.lang.Validate;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.IDynamicDataQuery;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.EntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author sleroy
 */
public abstract class AbstractDynamicQuery extends AbstractGroovyQuery implements
        IDynamicDataQuery<KpiResult>
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDynamicQuery.class);
    
    
    private final BackupDelay   delay;
    
    
    
    public AbstractDynamicQuery(final EntityType _entityType, final BackupDelay _delay) {
    
    
        super(_entityType);
        delay = _delay;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.engine.IQuery#getBackupDelay()
     */
    @Override
    public final BackupDelay getBackupDelay() {
    
    
        return delay;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.engine.IQuery#getResult()
     */
    @Override
    public final KpiResult getResult() {
    
    
        KpiResult res = new KpiResult();
        try {
            res = evaluateResult();
            Validate.notNull(res);
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            res = new KpiResult();
        }
        return res;
    }
    
    
    protected abstract KpiResult evaluateResult();
    
}
