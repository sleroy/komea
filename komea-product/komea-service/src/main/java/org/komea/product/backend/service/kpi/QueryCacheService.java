package org.komea.product.backend.service.kpi;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.komea.cep.dynamicdata.IDynamicDataQuery;
import org.komea.eventory.api.formula.ICEPResult;
import org.komea.eventory.formula.tuple.TupleResultMap;
import org.komea.eventory.query.CEPResult;
import org.komea.product.backend.api.IQueryCacheService;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QueryCacheService implements IQueryCacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger("dynamicquery-cache");
    private final LoadingCache<String, ICEPResult> cache;
    private final static String CRON_KEY = "DYNAMIC_QUERY_CACHE_CRON";

    @Autowired
    private ICronRegistryService cronRegistry;

    public QueryCacheService() {
        super();
        final CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
        cacheBuilder.expireAfterAccess(1, TimeUnit.DAYS);
        cacheBuilder.maximumSize(200);
        cache = cacheBuilder.build(new CacheLoader<String, ICEPResult>() {

            @Override
            public ICEPResult load(String key) throws Exception {
                return CEPResult.buildFromMap(new TupleResultMap());
            }
        });
    }

    private synchronized void registerCron() {
        if (!cronRegistry.existCron(CRON_KEY)) {
            System.out.println("QUERY CACHE SERVICE : registerCron");
            cronRegistry.registerCronTask(CRON_KEY, "0 0/5 * * * ? *", this.getClass(), new JobDataMap());
        }
    }

    @Override
    public IDynamicDataQuery addQueryInCache(final IDynamicDataQuery dynamicDataQuery) {
        System.out.println("QUERY CACHE SERVICE : addQueryInCache");
        registerCron();
        final Callable<ICEPResult> call = new Callable<ICEPResult>() {

            @Override
            public ICEPResult call() throws Exception {
                System.out.println("QUERY CACHE SERVICE : call");
                return dynamicDataQuery.getResult();
            }
        };
        return new IDynamicDataQuery() {

            @Override
            public synchronized ICEPResult getResult() {
                System.out.println("QUERY CACHE SERVICE : getResult");
                try {
                    return cache.get(getKey(), call);
                } catch (ExecutionException ex) {
                    LOGGER.error(ex.getMessage(), ex);
                    return CEPResult.buildFromMap(new TupleResultMap());
                }
            }

            @Override
            public String getKey() {
                return dynamicDataQuery.getKey();
            }
        };
    }

    @Override
    public synchronized void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("QUERY CACHE SERVICE : execute");
        final ConcurrentMap<String, ICEPResult> map = cache.asMap();
        for (final String queryKey : map.keySet()) {
            System.out.println("QUERY CACHE SERVICE : refresh " + queryKey);
            cache.refresh(queryKey);
        }
    }

}
