/**
 * 
 */

package org.komea.product.cep;



import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.lang.Validate;
import org.komea.product.cep.api.ICEPConfiguration;
import org.komea.product.cep.api.ICEPEngine;
import org.komea.product.cep.api.ICEPQueryEventListener;
import org.komea.product.cep.api.IQueryAdministrator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This class is the implementation of the Komea custom CEP Engine.
 * 
 * @author sleroy
 */

public class CEPEngine implements ICEPEngine
{
    
    
    private enum CEPEngineMode {
        DESTROYED, NOT_STARTED, RUNNING
    }
    
    
    
    private static final Logger    LOGGER        = LoggerFactory.getLogger("cep-engine");
    
    private final CEPConfiguration cepConfiguration;
    private ICEPQueryEventListener      eventListener = null;
    
    
    private CEPEngineMode          mode          = CEPEngineMode.NOT_STARTED;
    
    private IQueryAdministrator    queryAdministrator;
    
    
    
    /**
     * Builds the CEP Engine with the given configuration.
     * 
     * @param _cepConfiguration
     *            the cep configuration/
     */
    public CEPEngine(final CEPConfiguration _cepConfiguration) {
    
    
        cepConfiguration = _cepConfiguration;
        
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.io.Closeable#close()
     */
    @Override
    public void close() throws IOException {
    
    
        try {
            queryAdministrator = null;
            eventListener = null;
        } finally {
            mode = CEPEngineMode.DESTROYED;
        }
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPEngine#getConfiguration()
     */
    @Override
    public ICEPConfiguration getConfiguration() {
    
    
        return cepConfiguration;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPEngine#getQueryAdministration()
     */
    @Override
    public IQueryAdministrator getQueryAdministration() {
    
    
        return queryAdministrator;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPEngine#initialize()
     */
    @Override
    public void initialize() throws IOException {
    
    
        if (isInitialized()) {
            close();
        }
        final int res = cepConfiguration.getNumberQueryListeners();
        LOGGER.debug("CEP Engine starts with {} query listeners", res);
        eventListener = new CEPQueryListener();
        
        queryAdministrator = new QueryAdministrator(eventListener);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPEngine#pushEvent(org.komea.product.database.alert.Serializable)
     */
    @Override
    public void pushEvent(final Serializable _event) {
    
    
        Validate.notNull(_event);
        
        eventListener.notify(mode);
    }
    
    
    /**
     * Returns true if the engine is initialized.
     */
    private boolean isInitialized() {
    
    
        return mode == CEPEngineMode.RUNNING;
    }
    
}
