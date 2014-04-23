/**
 * 
 */

package org.komea.product.backend.service.kpi;



import java.util.Iterator;

import org.komea.cep.dynamicdata.IDynamicDataQuery;
import org.komea.eventory.api.formula.ICEPResult;
import org.komea.product.backend.api.IDynamicDataQueryRegisterService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * Query cache cron.
 * 
 * @author sleroy
 */
public class QueryCacheCron implements Job
{
    
    
    private static final Logger              LOGGER = LoggerFactory.getLogger(QueryCacheCron.class);
    
    private IDynamicDataQueryRegisterService queryAdministrator;
    
    
    
    @Override
    public void execute(final JobExecutionContext context) throws JobExecutionException {
    
    
        updateCacheOfDynamicQueries();
    }
    
    
    @Autowired
    public IDynamicDataQueryRegisterService getQueryAdministrator() {
    
    
        return queryAdministrator;
    }
    
    
    public void setQueryAdministrator(final IDynamicDataQueryRegisterService _queryAdministrator) {
    
    
        queryAdministrator = _queryAdministrator;
    }
    
    
    /**
     * Update cache of dynamic queries.
     */
    public void updateCacheOfDynamicQueries() {
    
    
        LOGGER.debug("Updating the cache of dynamic queries");
        final Iterator<IDynamicDataQuery> queriesIterator = queryAdministrator.getQueriesIterator();
        while (queriesIterator.hasNext()) {
            final ICEPResult result = queriesIterator.next().getResult();
            LOGGER.debug("Refresh the dynamic query {}", result);
        }
    }
    
    
}
