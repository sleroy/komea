/**
 *
 */

package org.komea.eventory.bridge;



import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.komea.eventory.api.bridge.IEventBridge;
import org.komea.eventory.api.engine.ICEPQuery;
import org.komea.eventory.api.engine.IQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * CEP Query Listener.
 *
 * @author sleroy
 */
public class MemoryBridge implements IEventBridge
{
    
    
    private static final Logger       LOGGER     = LoggerFactory.getLogger(MemoryBridge.class);
    
    private final Map<String, IQuery> queriesMap = new HashMap<String, IQuery>(100);
    
    
    
    /**
     */
    public MemoryBridge() {
    
    
        super();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.bridge.api.ICEPEngine#getQuery(java.lang.String)
     */
    @Override
    public IQuery getQuery(final String _query) {
    
    
        Validate.notNull(_query);
        
        return queriesMap.get(_query);
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.eventory.bridge.api.ICEPEventListener#notify(org.komea.product
     * .database.alert.Serializable)
     */
    @Override
    public void notify(final Serializable _event) {
    
    
        LOGGER.debug("Received a event {}", _event);
        
        for (final IQuery query : queriesMap.values()) {
            if (query instanceof ICEPQuery) { // FIXME::Improve this code
                ((ICEPQuery) query).notifyEvent(_event);
            }
        }
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.eventory.bridge.api.ICEPEngine#registerQuery(org.komea.eventory
     * .bridge.api.IQuery)
     */
    @Override
    public void registerQuery(final String _queryName, final IQuery _query) {
    
    
        Validate.notNull(_query);
        Validate.notNull(_queryName);
        LOGGER.debug("Register a query {}-> {}", _queryName, _query);
        if (queriesMap.containsKey(_query)) {
            throw new IllegalArgumentException("Query already defined : " + _query);
        }
        queriesMap.put(_queryName, _query);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.eventory.bridge.api.IQueryEventListener#removeQuery(java.lang
     * .String)
     */
    @Override
    public void removeQuery(final String _queryName) {
    
    
        Validate.notNull(_queryName);
        LOGGER.debug("Removes the query {}", _queryName);
        
        queriesMap.remove(_queryName);
        
    }
}
