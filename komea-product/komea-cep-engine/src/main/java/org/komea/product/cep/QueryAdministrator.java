/**
 * 
 */

package org.komea.product.cep;



import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.komea.product.cep.api.ICEPQuery;
import org.komea.product.cep.api.ICEPQueryEventListener;
import org.komea.product.cep.api.IQueryAdministrator;

import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;



/**
 * This class defines the query administrator
 * 
 * @author sleroy
 */
public class QueryAdministrator implements IQueryAdministrator
{
    
    
    private final ICEPQueryEventListener eventListener;
    private final Map<String, ICEPQuery> queryCatalog = new MapMaker().initialCapacity(100)
                                                              .makeMap();
    
    
    
    /**
     * Query administrator depends of event listener to register the queries inside the listener.
     * 
     * @param _eventListener
     *            the listener.
     */
    public QueryAdministrator(final ICEPQueryEventListener _eventListener) {
    
    
        eventListener = _eventListener;
        
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.IQueryAdministrator#existQuery(java.lang.String)
     */
    @Override
    public boolean existQuery(final String _engineKey) {
    
    
        return queryCatalog.containsKey(_engineKey);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.IQueryAdministrator#getQuery(java.lang.String)
     */
    @Override
    public ICEPQuery getQuery(final String _query) {
    
    
        return queryCatalog.get(_query);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.IQueryAdministrator#getQueryNames()
     */
    @Override
    public List<String> getQueryNames() {
    
    
        return Collections.unmodifiableList(Lists.newArrayList(queryCatalog.keySet().iterator()));
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.IQueryAdministrator#registerQuery(java.lang.String, org.komea.product.cep.api.ICEPQuery)
     */
    @Override
    public void registerQuery(final String _queryName, final ICEPQuery _query) {
    
    
        queryCatalog.put(_queryName, _query);
        eventListener.registerQuery(_queryName, _query);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.IQueryAdministrator#removeQuery(java.lang.String)
     */
    @Override
    public boolean removeQuery(final String _queryName) {
    
    
        final boolean wasPresent = queryCatalog.remove(_queryName) != null;
        eventListener.removeQuery(_queryName);
        return wasPresent;
    }
}
