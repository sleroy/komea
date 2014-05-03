
package org.komea.product.backend.service.dynamicquery;



import java.util.List;

import javax.annotation.PostConstruct;

import org.komea.product.backend.api.IDynamicDataQueryRegisterService;
import org.komea.product.backend.api.IDynamicQueryCacheService;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.cep.api.dynamicdata.IDynamicDataQuery;
import org.komea.product.database.dto.KpiResult;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;



/**
 * This service is designed to produce a wrapper over an dynamic query to avoid consequently loading of the results. The results are cached
 * and upgraded with a cron job that is polled every fixed interval. This provides a boost in performance.
 * 
 * @author sleroy
 */
@Service
@Transactional
public class DynamicQueryCacheService implements IDynamicQueryCacheService
{
    
    
    private static final Logger              LOGGER          =
                                                                     LoggerFactory
                                                                             .getLogger("dynamicquery-cache");
    private Cache<String, KpiResult>         cache;
    
    
    private final String                     CRON_EXPRESSION = "0 0/5  * * * ?";
    @Autowired
    private ICronRegistryService             cronRegistryService;
    
    
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
    
    
        LOGGER.debug("add a dynamic query into the cache {}", _queryKey);
        
        return new CachedDynamicQuery(this, _dynamicDataQuery, _queryKey);
    }
    
    
    /**
     * Returns the cache.
     * 
     * @return the cache.
     */
    public Cache<String, KpiResult> getCache() {
    
    
        return cache;
    }
    
    
    public IDynamicDataQueryRegisterService getQueryRegisterService() {
    
    
        return queryRegisterService;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.api.IDynamicQueryCacheService#storedQueryNames()
     */
    @Override
    public List<String> getStoredQueryNames() {
    
    
        return Lists.newArrayList(cache.asMap().keySet());
    }
    
    
    @PostConstruct
    public void init() {
    
    
        LOGGER.info("Initializing the cache for queries");
        final CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
        cacheBuilder.initialCapacity(50);
        cache = cacheBuilder.build();
        cronRegistryService.registerCronTask("DYNAMIC_QUERY_CACHE_REFRESH_CRON", CRON_EXPRESSION,
                QueryCacheRefreshCron.class, new JobDataMap());
        
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.api.IDynamicQueryCacheService#refreshValue(java.lang.String,
     * org.komea.product.cep.api.formula.KpiResult)
     */
    @Override
    public void refreshValue(final String _queryKey, final KpiResult _result) {
    
    
        cache.put(_queryKey, _result);
        
    }
    
    
    public void setQueryRegisterService(final IDynamicDataQueryRegisterService _queryRegisterService) {
    
    
        queryRegisterService = _queryRegisterService;
    }
    
    
}
