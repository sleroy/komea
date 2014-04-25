/**
 * 
 */

package org.komea.eventory;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.Validate;
import org.komea.eventory.api.bridge.IEventBridge;
import org.komea.eventory.api.engine.ICEPQuery;
import org.komea.eventory.api.engine.IQueryAdministrator;



/**
 * This class defines the query administrator
 * 
 * @author sleroy
 */

public class QueryAdministrator implements IQueryAdministrator
{
    
    
    private final IEventBridge           eventBridge;
    private final Map<String, ICEPQuery> queryCatalog = new ConcurrentHashMap<String, ICEPQuery>(
                                                              100);
    
    
    
    /**
     * Query administrator depends of event listener to register the queries inside the listener.
     * 
     * @param _eventListener
     *            the listener.
     */
    public QueryAdministrator(final IEventBridge _eventListener) {
    
    
        super();
        
        eventBridge = _eventListener;
        Validate.notNull(_eventListener);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.IQueryAdministrator#existQuery(java.lang.String)
     */
    @Override
    public boolean existQuery(final String _engineKey) {
    
    
        Validate.notEmpty(_engineKey);
        return queryCatalog.containsKey(_engineKey);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.IQueryAdministrator#getQuery(java.lang.String)
     */
    @Override
    public ICEPQuery getQuery(final String _query) {
    
    
        Validate.notEmpty(_query);
        return queryCatalog.get(_query);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.IQueryAdministrator#getQueryNames()
     */
    @Override
    public List<String> getQueryNames() {
    
    
        return Collections.unmodifiableList(new ArrayList<String>(queryCatalog.keySet()));
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.IQueryAdministrator#registerQuery(java.lang.String, org.komea.eventory.api.ICEPQuery)
     */
    @Override
    public void registerQuery(final String _queryName, final ICEPQuery _query) {
    
    
        Validate.notEmpty(_queryName);
        Validate.notNull(_query);
        
        queryCatalog.put(_queryName, _query);
        eventBridge.registerQuery(_queryName, _query);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.IQueryAdministrator#removeQuery(java.lang.String)
     */
    @Override
    public boolean removeQuery(final String _queryName) {
    
    
        Validate.notEmpty(_queryName);
        
        final boolean wasPresent = queryCatalog.remove(_queryName) != null;
        eventBridge.removeQuery(_queryName);
        return wasPresent;
    }
}
