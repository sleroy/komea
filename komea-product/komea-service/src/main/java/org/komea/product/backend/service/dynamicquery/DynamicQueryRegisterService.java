/**
 * 
 */

package org.komea.product.backend.service.dynamicquery;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.komea.cep.dynamicdata.IDynamicDataQuery;
import org.komea.product.backend.api.IDynamicDataQueryRegisterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * This services stores the queries that are computed on dynamic data and not events.
 * 
 * @author sleroy
 */
@Transactional
@Service
public class DynamicQueryRegisterService implements IDynamicDataQueryRegisterService
{
    
    
    private final Map<String, IDynamicDataQuery> queryCatalog = new ConcurrentHashMap(100);
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.IQueryAdministrator#existQuery(java.lang.String)
     */
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IDynamicDataQueryRegisterService#existQuery(java.lang.String)
     */
    @Override
    public boolean existQuery(final String _engineKey) {
    
    
        return queryCatalog.containsKey(_engineKey);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.IQueryAdministrator#getQuery(java.lang.String)
     */
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.api.IDynamicDataQueryRegisterService#getQueriesIterator()
     */
    @Override
    public Iterator<IDynamicDataQuery> getQueriesIterator() {
    
    
        return queryCatalog.values().iterator();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.IQueryAdministrator#getQueryNames()
     */
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IDynamicDataQueryRegisterService#getQuery(java.lang.String)
     */
    @Override
    public IDynamicDataQuery getQuery(final String _query) {
    
    
        return queryCatalog.get(_query);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.IQueryAdministrator#registerQuery(java.lang.String, org.komea.eventory.api.IDynamicDataQuery)
     */
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IDynamicDataQueryRegisterService#getQueryNames()
     */
    @Override
    public List<String> getQueryNames() {
    
    
        return Collections.unmodifiableList(new ArrayList<String>(queryCatalog.keySet()));
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.IQueryAdministrator#removeQuery(java.lang.String)
     */
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IDynamicDataQueryRegisterService#registerQuery(java.lang.String,
     * org.komea.cep.dynamicdata.IDynamicDataQuery)
     */
    @Override
    public void registerQuery(final String _queryName, final IDynamicDataQuery _query) {
    
    
        queryCatalog.put(_queryName, _query);
        
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IDynamicDataQueryRegisterService#removeQuery(java.lang.String)
     */
    @Override
    public boolean removeQuery(final String _queryName) {
    
    
        return queryCatalog.remove(_queryName) != null;
    }
}
