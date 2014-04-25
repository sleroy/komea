/**
 * 
 */

package org.komea.product.backend.service.dynamicquery;



import org.apache.commons.lang.Validate;
import org.komea.cep.dynamicdata.IDynamicDataQuery;
import org.komea.eventory.api.formula.ICEPResult;
import org.komea.eventory.formula.tuple.TupleResultMap;
import org.komea.eventory.query.CEPResult;
import org.komea.product.backend.api.IDynamicDataQueryRegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheLoader;



/**
 * @author sleroy
 */
public final class DynamicQueryCacheLoader extends CacheLoader<String, ICEPResult>
{
    
    
    private static final Logger                    LOGGER =
                                                                  LoggerFactory
                                                                          .getLogger("dynamic-query-loader");
    private final IDynamicDataQueryRegisterService queryRegisterService;
    
    
    
    /**
     * @param _queryRegisterService
     */
    public DynamicQueryCacheLoader(final IDynamicDataQueryRegisterService _queryRegisterService) {
    
    
        super();
        queryRegisterService = _queryRegisterService;
        Validate.notNull(_queryRegisterService);
    }
    
    
    @Override
    public ICEPResult load(final String key) throws Exception {
    
    
        LOGGER.info("Refreshing value for the kpi {}", key);
        final IDynamicDataQuery query = queryRegisterService.getQuery(key);
        if (query == null) { return CEPResult.buildFromMap(new TupleResultMap()); }
        if (query instanceof CachedDynamicQuery) {
            // Skip cache invocation.
            final CachedDynamicQuery cachedDynamicQuery = (CachedDynamicQuery) query;
            return cachedDynamicQuery.getDynamicDataQuery().getResult();
        } else {
            return query.getResult();
        }
    }
}
