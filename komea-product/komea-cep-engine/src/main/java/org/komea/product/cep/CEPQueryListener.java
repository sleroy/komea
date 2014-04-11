/**
 * 
 */

package org.komea.product.cep;



import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.komea.product.cep.api.ICEPQuery;
import org.komea.product.cep.api.ICEPQueryEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * CEP Query Listener.
 * 
 * @author sleroy
 */
public class CEPQueryListener implements ICEPQueryEventListener
{
    
    
    private static final Logger          LOGGER     = LoggerFactory
                                                            .getLogger(CEPQueryListener.class);
    
    
    private final Map<String, ICEPQuery> queriesMap = new HashMap<String, ICEPQuery>(100);
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPEngine#getQuery(java.lang.String)
     */
    @Override
    public ICEPQuery getQuery(final String _query) {
    
    
        Validate.notNull(_query);
        
        return queriesMap.get(_query);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPEventListener#notify(org.komea.product.database.alert.Serializable)
     */
    @Override
    public void notify(final Serializable _event) {
    
    
        LOGGER.debug("Received a event {}", _event);
        
        for (final ICEPQuery query : queriesMap.values()) {
            query.notifyEvent(_event);
        }
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPEngine#registerQuery(org.komea.product.cep.api.ICEPQuery)
     */
    @Override
    public void registerQuery(final String _queryName, final ICEPQuery _query) {
    
    
        Validate.notNull(_query);
        Validate.notNull(_queryName);
        LOGGER.debug("Register a query {}-> {}", _queryName, _query);
        if (queriesMap.containsKey(_query)) { throw new IllegalArgumentException(
                "Query already defined : " + _query); }
        queriesMap.put(_queryName, _query);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPQueryEventListener#removeQuery(java.lang.String)
     */
    @Override
    public void removeQuery(final String _queryName) {
    
    
        Validate.notNull(_queryName);
        LOGGER.debug("Removes the query {}", _queryName);
        
        queriesMap.remove(_queryName);
        
    }
}
