/**
 *
 */

package org.komea.product.backend.groovy;



import org.apache.commons.lang.Validate;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.IDynamicDataQuery;
import org.komea.product.database.dto.KpiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author sleroy
 */
public abstract class AbstractDynamicQuery extends AbstractGroovyQuery implements
        IDynamicDataQuery<KpiResult>
{
    
    
    private final BackupDelay delay;
    
    
    protected final Logger    LOGGER = LoggerFactory.getLogger(getClass());
    
    
    
    public AbstractDynamicQuery(final BackupDelay _delay) {
    
    
        super();
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
    public final KpiResult getResult() throws QueryExecutionFailed {
    
    
        KpiResult res = new KpiResult();
        try {
            res = evaluateResult();
            Validate.notNull(res);
        } catch (final Exception e) {
            res.hasFailed(e);
            LOGGER.error(e.getMessage(), e);
        }
        return res;
    }
    
    
    protected abstract KpiResult evaluateResult();
    
}
