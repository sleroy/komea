
package org.komea.product.backend.service.dynamicquery;



import java.util.List;
import java.util.concurrent.TimeUnit;

import org.komea.cep.dynamicdata.IDynamicDataQuery;
import org.komea.eventory.api.formula.ICEPResult;
import org.komea.product.backend.api.IDynamicDataQueryRegisterService;
import org.komea.product.backend.api.IQueryCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;



/**
 * This service is designed to produce a wrapper over an dynamic query to avoid consequently loading of the results. The results are cached
 * and upgraded with a cron job that is polled every fixed interval. This provides a boost in performance.
 * 
 * @author sleroy
 */
@Service
@Transactional
public class DynamicQueryCacheService implements IQueryCacheService
{
    
    
    private static final Logger                    LOGGER = LoggerFactory
                                                                  .getLogger("dynamicquery-cache");
    private final LoadingCache<String, ICEPResult> cache;
    
    
    @Autowired
    private IDynamicDataQueryRegisterService       queryRegisterService;
    
    
    
    /**
     * Initialize the query cache service.
     */
    public DynamicQueryCacheService() {
    
    
        super();
        final CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
        cacheBuilder.refreshAfterWrite(6, TimeUnit.HOURS);
        cacheBuilder.maximumSize(200);
        cacheBuilder.initialCapacity(50);
        cache = cacheBuilder.build(new DynamicQueryCacheLoader(queryRegisterService));
    }
    
    
    /**
     * Adds a query in cache. The wrapped query is returned frmo this method.
     */
    @Override
    public IDynamicDataQuery addCacheOnDynamicQuery(
            final String _queryKey,
            final IDynamicDataQuery _dynamicDataQuery) {
    
    
        LOGGER.info("add a query in cache {}", _dynamicDataQuery);
        
        return new CachedDynamicQuery(this, _dynamicDataQuery, _queryKey);
    }
    
    
    /**
     * Returns the cache.
     * 
     * @return the cache.
     */
    public LoadingCache<String, ICEPResult> getCache() {
    
    
        return cache;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.api.IQueryCacheService#storedQueryNames()
     */
    @Override
    public List<String> getStoredQueryNames() {
    
    
        return Lists.newArrayList(cache.asMap().keySet());
    }
    
    
}
