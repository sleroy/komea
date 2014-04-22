package org.komea.product.backend.service.kpi;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.komea.cep.dynamicdata.IDynamicDataQuery;
import org.komea.eventory.api.formula.ICEPResult;
import org.komea.eventory.formula.tuple.TupleResultMap;
import org.komea.eventory.query.CEPResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.util.Validate;

/**
 * DynamicCacheQuery.java (UTF-8)
 *
 * 22 avr. 2014
 *
 * @author scarreau
 */
public class DynamicCacheQuery implements IDynamicDataQuery, Callable<ICEPResult> {

    private static final Logger LOGGER = LoggerFactory.getLogger("dynamicquery-cache");
    private final IDynamicDataQuery dataQuery;
    private Cache<String, ICEPResult> resultCache;

    public DynamicCacheQuery(IDynamicDataQuery _dataQuery
    ) {
        super();
        this.dataQuery = _dataQuery;
        initCache();
    }

    @Override
    public synchronized ICEPResult getResult() {
        LOGGER.info("Obtaining value from query {}", dataQuery);
        try {
            final ICEPResult get = resultCache.get(KEY_QUERY, this);
            LOGGER.info("Obtaining value from query {} result {}", dataQuery, get);
            return get;
        } catch (ExecutionException ex) {
            LOGGER.error("Could not retrieve value from query {}", dataQuery, ex);
            return CEPResult.buildFromMap(new TupleResultMap());
        }
    }
    public static final String KEY_QUERY = "value";

    private void initCache() {
        final CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder();

        builder.expireAfterWrite(30,
                TimeUnit.MINUTES);

        builder.maximumSize(1);
        resultCache = builder.build();
    }

    /**
     * Returns the new value.
     *
     * @return the value.
     * @throws Exception
     */
    @Override
    public ICEPResult call() throws Exception {
        LOGGER.info("value not in cache, producing it {}", dataQuery);
        final ICEPResult result = dataQuery.getResult();

        Validate.notNull(result, "Query should never return null " + dataQuery);

        return result;
    }

}
