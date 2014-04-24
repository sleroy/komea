package org.komea.product.backend.service.kpi;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.komea.cep.dynamicdata.IDynamicDataQuery;
import org.komea.eventory.api.formula.ICEPResult;
import org.komea.product.backend.api.IQueryCacheService;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This service is designed to produce a wrapper over an dynamic query to avoid
 * consequently loading of the results. The results are cached and upgraded with
 * a cron job that is polled every fixed interval. This provides a boost in
 * performance.
 *
 * @author sleroy
 */
@Service
@Transactional
public class QueryCacheService implements IQueryCacheService {

    private final static String CRON_KEY = "DYNAMIC_QUERY_CACHE_CRON";
    /**
     *
     */
    private static final String CRON_PERIOD = "0 0/5 * * * ? *";
    private static final Logger LOGGER
            = LoggerFactory
            .getLogger("dynamicquery-cache");
    private final LoadingCache<String, ICEPResult> cache;

    @Autowired
    private ICronRegistryService cronRegistry;

    /**
     * Initialize the query cache service.
     */
    public QueryCacheService() {

        super();
        final CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
        cacheBuilder.expireAfterAccess(1, TimeUnit.DAYS);
        cacheBuilder.maximumSize(200);
        cache = cacheBuilder.build(new DynamicQueryCacheLoader());
    }

    /**
     * Adds a query in cache. The wrapped query is returned frmo this method.
     */
    @Override
    public IDynamicDataQuery addCacheOnDynamicQuery(final IDynamicDataQuery _dynamicDataQuery) {

        LOGGER.info("add a query in cache {}", _dynamicDataQuery);

        return new CachedDynamicQuery(this, _dynamicDataQuery);
    }

    /**
     * Returns the cache.
     *
     * @return the cache.
     */
    public LoadingCache<String, ICEPResult> getCache() {

        return cache;
    }

    public ICronRegistryService getCronRegistry() {

        return cronRegistry;
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

        LOGGER.info("Initialization of cache query engine");
        registerCron();

    }

    /**
     * Register the cron.
     */
    public void registerCron() {

        if (!cronRegistry.existCron(CRON_KEY)) {
            cronRegistry.registerCronTask(CRON_KEY, CRON_PERIOD, QueryCacheCron.class,
                    new JobDataMap());
        }
    }

    public void setCronRegistry(final ICronRegistryService _cronRegistry) {

        cronRegistry = _cronRegistry;
    }

    @Override
    public void refreshAll() {
        final List<String> storedQueryNames = getStoredQueryNames();
        for (final String storedQueryName : storedQueryNames) {
            LOGGER.info("refresh query " + storedQueryName);
            cache.refresh(storedQueryName);
        }
    }

}
