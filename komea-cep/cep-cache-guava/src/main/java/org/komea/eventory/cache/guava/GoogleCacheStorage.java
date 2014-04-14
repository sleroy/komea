/**
 * 
 */

package org.komea.eventory.cache.guava;



import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.Validate;
import org.komea.eventory.api.cache.ICacheConfiguration;
import org.komea.eventory.api.cache.ICacheIndexer;
import org.komea.eventory.api.cache.ICacheStorage;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;



/**
 * This class defines the implementation of the cache storage.
 * 
 * @author sleroy
 */
public final class GoogleCacheStorage<T extends Serializable> implements ICacheStorage<T>
{
    
    
    private Cache<Object, T>    cacheBuilder = null;
    
    private final long          id           = 0;
    
    private ICacheIndexer<T, ?> indexer      = new HashCodeCacheIndexer<T>();
    
    
    
    /**
     * Cache storage.
     */
    public GoogleCacheStorage(final ICacheConfiguration _cacheConfiguration) {
    
    
        super();
        
        createCache(_cacheConfiguration);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICacheStorage#clear()
     */
    @Override
    public void clear() {
    
    
        cacheBuilder.invalidateAll();
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.cache.ICacheStorage#getAllValues()
     */
    @Override
    public Collection<T> getAllValues() {
    
    
        return cacheBuilder.asMap().values();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICacheStorage#getItems()
     */
    @Override
    public Iterator<T> getIterator() {
    
    
        return cacheBuilder.asMap().values().iterator();
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICacheStorage#push(java.lang.Object)
     */
    @Override
    public synchronized void push(final T _item) {
    
    
        cacheBuilder.put(getKeyFromEvent(_item), _item);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.cache.ICacheStorage#remove(java.lang.Object)
     */
    @Override
    public void remove(final T _event) {
    
    
        this.cacheBuilder.invalidate(getKeyFromEvent(_event));
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICacheStorage#size()
     */
    @Override
    public long size() {
    
    
        return cacheBuilder.size();
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "GoogleCacheStorage [cacheBuilder=" + cacheBuilder + ", id=" + id + "]";
    }
    
    
    /**
     * Initializes the cache from a cache configuratioN.
     * 
     * @param _cacheConfiguration
     *            the cache configuration.
     */
    private void createCache(final ICacheConfiguration _cacheConfiguration) {
    
    
        final CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder();
        if (_cacheConfiguration.hasTimePolicy()) {
            builder.expireAfterWrite(_cacheConfiguration.getTime(),
                    _cacheConfiguration.getTimeUnit());
        }
        if (_cacheConfiguration.hasMaximumSizePolicy()) {
            builder.maximumSize(_cacheConfiguration.getMaximumSize());
        }
        if (_cacheConfiguration.isEnableStats()) {
            builder.recordStats();
        }
        if (_cacheConfiguration.hasCustomIndexer()) {
            indexer = _cacheConfiguration.getCustomIndexer();
        }
        cacheBuilder = builder.build();
    }
    
    
    private Object getKeyFromEvent(final T _event) {
    
    
        Validate.notNull(_event);
        return indexer.getKey(_event);
    }
}
