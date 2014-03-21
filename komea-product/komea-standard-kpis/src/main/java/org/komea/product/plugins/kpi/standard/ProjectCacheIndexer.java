/**
 * 
 */

package org.komea.product.plugins.kpi.standard;



import org.komea.product.cep.api.cache.ICacheIndexer;
import org.komea.product.database.alert.IEvent;
import org.komea.product.service.dto.EntityKey;



/**
 * This class indexes events by project entity key, no duplicate with project should be found.
 * 
 * @author sleroy
 */
public class ProjectCacheIndexer implements ICacheIndexer<IEvent, EntityKey>
{
    
    
    /**
     * 
     */
    public ProjectCacheIndexer() {
    
    
        super();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.cache.ICacheIndexer#getKey(java.io.Serializable)
     */
    @Override
    public EntityKey getKey(final IEvent _event) {
    
    
        if (_event.getProject() == null) { return new EntityKey(); }
        return _event.getProject().getEntityKey();
    }
    
}
