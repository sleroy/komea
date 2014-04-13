/**
 * 
 */

package org.komea.eventory.cache;



import java.io.Serializable;

import org.komea.product.cep.api.cache.ICacheIndexer;



/**
 * This class returns the key from an event based on its own hashcode.
 * 
 * @author sleroy
 */
public class HashCodeCacheIndexer<T extends Serializable> implements ICacheIndexer<T, Integer>
{
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.cache.ICacheIndexer#getKey(java.io.Serializable)
     */
    @Override
    public Integer getKey(final T _event) {
    
    
        return Integer.valueOf(_event.hashCode());
    }
    
    
}
