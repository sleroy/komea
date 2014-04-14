/**
 * 
 */

package org.komea.eventory.api.cache;



import java.io.Serializable;



/**
 * This interface defines the method to implement to provide the key for the cache storage.
 * 
 * @author sleroy
 */
public interface ICacheIndexer<T extends Serializable, K>
{
    
    
    K getKey(final T _event);
}
