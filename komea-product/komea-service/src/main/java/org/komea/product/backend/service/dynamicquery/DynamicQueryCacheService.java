
package org.komea.product.backend.service.dynamicquery;



import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

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
    
    
    private static final Logger              LOGGER = LoggerFactory.getLogger("dynamicquery-cache");
    private LoadingCache<String, ICEPResult> cache;
    
    
    @Autowired
    private IDynamicDataQueryRegisterService queryRegisterService;
    
    
    
    /**
     * Initialize the query cache service.
     */
    public DynamicQueryCacheService() {
    
    
        super();
        
    }
    
    
    /**
     * Adds a query in cache. The wrapped query is returned frmo this method.
     */
    @Override
    public IDynamicDataQuery addCacheOnDynamicQuery(
            final String _queryKey,
            final IDynamicDataQuery _dynamicDataQuery) {
    
    
        LOGGER.info("add a dynamic query into the cache {}", _queryKey);
        
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
    
    
    public IDynamicDataQueryRegisterService getQueryRegisterService() {
    
    
        return queryRegisterService;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.api.IQueryCacheService#storedQueryNames()
     */
    @Override
    public List<String> getStoredQueryNames() {
    
    
        return Lists.newArrayList(cache.asMap().keySet());
    }
    
    
    @PostConstruct
    public void init() {
    
    
        LOGGER.info("Initializing the cache for queries");
        final CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
        cacheBuilder.refreshAfterWrite(6, TimeUnit.HOURS);
        cacheBuilder.maximumSize(200);
        cacheBuilder.initialCapacity(50);
        cache = cacheBuilder.build(new DynamicQueryCacheLoader(queryRegisterService));
    }
    
    
    public void setQueryRegisterService(final IDynamicDataQueryRegisterService _queryRegisterService) {
    
    
        queryRegisterService = _queryRegisterService;
    }
    
    
}
