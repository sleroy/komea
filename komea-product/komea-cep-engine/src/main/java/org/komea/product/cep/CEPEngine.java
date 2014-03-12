/**
 * 
 */

package org.komea.product.cep;



import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.komea.product.cep.api.ICEPEngine;
import org.komea.product.cep.api.ICEPEventListener;
import org.springframework.stereotype.Service;



/**
 * This class is the implementation of the Komea custom CEP Engine.
 * 
 * @author sleroy
 */
@Service
public class CEPEngine implements ICEPEngine
{
    
    
    private final Map<String, ICEPEventListener> eventListeners =
                                                                        new HashMap<String, ICEPEventListener>();
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPEngine#pushEvent(org.komea.product.database.alert.Serializable)
     */
    @Override
    public void pushEvent(final Serializable _event) {
    
    
        Validate.notNull(_event);
        
        for (final ICEPEventListener cepQuery : eventListeners.values()) {
            cepQuery.notify(_event);
        }
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPEngine#registerEventListener(java.lang.String, org.komea.product.cep.api.ICEPEventListener)
     */
    @Override
    public void registerEventListener(final String _name, final ICEPEventListener _listener) {
    
    
        Validate.notNull(_name);
        Validate.notNull(_listener);
        eventListeners.put(_name, _listener);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPEngine#removeEventListener(java.lang.String)
     */
    @Override
    public void removeEventListener(final String _name) {
    
    
        Validate.notNull(_name);
        
        eventListeners.remove(_name);
        
    }
    
}
