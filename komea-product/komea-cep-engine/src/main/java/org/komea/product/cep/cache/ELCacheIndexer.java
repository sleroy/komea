/**
 * 
 */

package org.komea.product.cep.cache;



import org.komea.product.cep.api.cache.ICacheIndexer;
import org.komea.product.cep.formula.ElFormula;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.api.IEntity;



/**
 * This class defines a cache indexer using an EL Formula;
 * 
 * @author sleroy
 */
public class ELCacheIndexer implements ICacheIndexer<IEvent, Object>
{
    
    
    private final String groupBy;
    
    
    
    /**
     * 
     */
    public ELCacheIndexer(final String _groupBy) {
    
    
        super();
        groupBy = _groupBy;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.cache.ICacheIndexer#getKey(java.io.Serializable)
     */
    @Override
    public Object getKey(final IEvent _event) {
    
    
        final ElFormula elFormula = new ElFormula(groupBy, Object.class);
        final Object value = elFormula.getValue(_event);
        if (value instanceof IEntity) { return ((IEntity) value).getEntityKey(); }
        return value;
    }
    
}
