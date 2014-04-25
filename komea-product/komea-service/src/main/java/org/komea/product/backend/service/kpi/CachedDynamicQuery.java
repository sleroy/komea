/**
 *
 */
package org.komea.product.backend.service.kpi;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import org.apache.commons.lang.Validate;
import org.komea.cep.dynamicdata.IDynamicDataQuery;
import org.komea.eventory.api.formula.ICEPResult;
import org.komea.eventory.formula.tuple.TupleResultMap;
import org.komea.eventory.query.CEPResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class defines the implementation of a cache query.
 *
 * @author sleroy
 */
public final class CachedDynamicQuery implements IDynamicDataQuery, Callable<ICEPResult> {

    private static final Logger LOGGER = LoggerFactory.getLogger("cached-dynamicquery");

    private final IDynamicDataQuery dynamicDataQuery;

    private final QueryCacheService queryCacheService;

    public CachedDynamicQuery(
            final QueryCacheService _queryCacheService,
            final IDynamicDataQuery _dynamicDataQuery) {

        super();
        queryCacheService = _queryCacheService;
        dynamicDataQuery = _dynamicDataQuery;
        Validate.notNull(_dynamicDataQuery);
        Validate.notNull(_queryCacheService);
    }

    /*
     * (non-Javadoc)
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public ICEPResult call() throws Exception {

        LOGGER.debug("Returning a fresh value from the query");
        return dynamicDataQuery.getResult();
    }

    @Override
    public String getFormula() {

        return dynamicDataQuery.getFormula();
    }

    @Override
    public synchronized ICEPResult getResult() {

        LOGGER.debug("Request value from query with cached result {}", dynamicDataQuery);
        try {
            return returnValueFromCacheOrFreshValue();
        } catch (final ExecutionException ex) {
            return returnDefaultValueIfErrors(ex);
        }
    }

    private ICEPResult returnDefaultValueIfErrors(final ExecutionException ex) {

        LOGGER.error(ex.getMessage(), ex);
        return CEPResult.buildFromMap(new TupleResultMap());
    }

    private ICEPResult returnValueFromCacheOrFreshValue() throws ExecutionException {

        LOGGER.debug("Cache state {} values and {}", queryCacheService.getCache().size(), getFormula());
        final ICEPResult result = queryCacheService.getCache().get(getFormula(), this);
        LOGGER.debug("Cache state after retrieving {} values and {}", queryCacheService.getCache()
                .size(), getFormula());
        return result;
    }
}
