/**
 * 
 */

package org.komea.eventory.api.cache;






/**
 * This interface defines the factory to build a cache storage
 * 
 * @author sleroy
 */
public interface ICacheStorageFactory
{
    
    
    /**
     * Builds a new cache storage.
     * 
     * @param _cacheConfiguration
     *            the cache configuration.
     * @return the cache storage.
     */
    ICacheStorage newCacheStorage(ICacheConfiguration _cacheConfiguration);
}
