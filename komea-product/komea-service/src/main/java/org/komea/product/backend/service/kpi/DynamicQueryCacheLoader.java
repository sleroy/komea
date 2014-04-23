/**
 * 
 */

package org.komea.product.backend.service.kpi;



import org.komea.eventory.api.formula.ICEPResult;
import org.komea.eventory.formula.tuple.TupleResultMap;
import org.komea.eventory.query.CEPResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheLoader;



/**
 * @author sleroy
 */
public final class DynamicQueryCacheLoader extends CacheLoader<String, ICEPResult>
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger("dynamic-query-loader");
    
    
    
    @Override
    public ICEPResult load(final String key) throws Exception {
    
    
        LOGGER.debug("Initializing the default query cache result.");
        return CEPResult.buildFromMap(new TupleResultMap());
    }
}
