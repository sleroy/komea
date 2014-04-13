/**
 * 
 */

package org.komea.product.cep.filter;



import java.io.Serializable;

import org.komea.eventory.api.cache.ICacheIndexer;
import org.komea.product.cep.formula.ElFormula;



/**
 * This class defines a cache indexer using an EL Formula;
 * 
 * @author sleroy
 */
public class ELCacheIndexer implements ICacheIndexer<Serializable, Object>
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
    public Object getKey(final Serializable _event) {
    
    
        final ElFormula elFormula = new ElFormula(groupBy, Object.class);
        final Object value = elFormula.getValue(_event);
        return value;
    }
    
}
