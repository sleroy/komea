/**
 * 
 */

package org.komea.product.backend.service.dynamicquery;



import org.apache.commons.lang.Validate;
import org.komea.cep.dynamicdata.IDynamicDataQuery;
import org.komea.product.database.dto.KpiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This class defines the implementation of a cache query.
 * 
 * @author sleroy
 */
public final class CachedDynamicQuery implements IDynamicDataQuery
{
    
    
    private static final Logger            LOGGER = LoggerFactory.getLogger("cached-dynamicquery");
    
    private final IDynamicDataQuery        dynamicDataQuery;
    
    private final DynamicQueryCacheService dynamicQueryCacheService;
    
    private final String                   queryKey;
    
    
    
    public CachedDynamicQuery(
            final DynamicQueryCacheService _dynamicQueryCacheService,
            final IDynamicDataQuery _dynamicDataQuery,
            final String _key) {
    
    
        super();
        dynamicQueryCacheService = _dynamicQueryCacheService;
        dynamicDataQuery = _dynamicDataQuery;
        queryKey = _key;
        Validate.notNull(_dynamicDataQuery);
        Validate.notNull(_dynamicQueryCacheService);
    }
    
    
    public IDynamicDataQuery getDynamicDataQuery() {
    
    
        return dynamicDataQuery;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.cep.dynamicdata.IDynamicDataQuery#getFormula()
     */
    @Override
    public String getFormula() {
    
    
        return dynamicDataQuery.getFormula();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.cep.dynamicdata.IDynamicDataQuery#getResult()
     */
    @Override
    public KpiResult getResult() {
    
    
        LOGGER.debug("Cache state {} values and {}", dynamicQueryCacheService.getCache().size(),
                queryKey);
        final KpiResult result = returnValueFromCacheOrFreshValue();
        LOGGER.debug("Cache state after retrieving {} values and {}", dynamicQueryCacheService
                .getCache().size(), queryKey);
        return result;
        
    }
    
    
    private KpiResult returnValueFromCacheOrFreshValue() {
    
    
        LOGGER.debug("Cache state {} values and {}", dynamicQueryCacheService.getCache().size());
        KpiResult result = dynamicQueryCacheService.getCache().getIfPresent(queryKey);
        if (null == result) {
            result = dynamicDataQuery.getResult();
        }
        if (null == result) { return KpiResult.EMPTY; }
        LOGGER.debug("Cache state after retrieving {} values and {}", dynamicQueryCacheService
                .getCache().size());
        return result;
    }
}
