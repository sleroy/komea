/**
 * 
 */

package org.komea.product.plugins.kpi.standard;



import org.komea.product.cep.api.cache.ICacheIndexer;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.model.Project;



/**
 * @author sleroy
 */
public class ProjectCacheIndexer implements ICacheIndexer<IEvent, Project>
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
    public Project getKey(final IEvent _event) {
    
    
        return _event.getProject();
    }
    
}
