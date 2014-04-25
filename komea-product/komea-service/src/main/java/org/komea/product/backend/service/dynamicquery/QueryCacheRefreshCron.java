/**
 * 
 */

package org.komea.product.backend.service.dynamicquery;



import java.util.List;

import org.apache.commons.lang.Validate;
import org.komea.cep.dynamicdata.IDynamicDataQuery;
import org.komea.eventory.api.formula.ICEPResult;
import org.komea.product.backend.api.IDynamicDataQueryRegisterService;
import org.komea.product.backend.api.IDynamicQueryCacheService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author sleroy
 */
public class QueryCacheRefreshCron implements Job
{
    
    
    private static final Logger              LOGGER = LoggerFactory
                                                            .getLogger(QueryCacheRefreshCron.class);
    
    @Autowired
    private IDynamicQueryCacheService               dynamicQueryCacheService;
    
    
    @Autowired
    private IDynamicDataQueryRegisterService queryRegisterService;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(final JobExecutionContext _context) throws JobExecutionException {
    
    
        executeCron();
        
        
    }
    
    
    public void executeCron() {
    
    
        final List<String> queriesIterator = queryRegisterService.getQueryNames();
        for (final String queryKey : queriesIterator) {
            
            LOGGER.info("Refreshing value for the query {}", queryKey);
            final IDynamicDataQuery query = queryRegisterService.getQuery(queryKey);
            Validate.notNull(queryKey);
            ICEPResult refreshedValue;
            try {
                refreshedValue = refreshValueFromQueryByKey(query);
                
                dynamicQueryCacheService.refreshValue(queryKey, refreshedValue);
            } catch (final Exception e) {
                LOGGER.error("Error during the refreshing of the query {}", queryKey);
            }
            
        }
    }
    
    
    /**
     * Loads a new value.
     */
    public ICEPResult refreshValueFromQueryByKey(final IDynamicDataQuery _query) throws Exception {
    
    
        if (_query instanceof CachedDynamicQuery) {
            // Skip cache invocation.
            final CachedDynamicQuery cachedDynamicQuery = (CachedDynamicQuery) _query;
            return cachedDynamicQuery.getDynamicDataQuery().getResult();
        } else {
            return _query.getResult();
        }
    }
}
