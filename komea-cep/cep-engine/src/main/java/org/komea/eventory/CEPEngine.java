/**
 * 
 */

package org.komea.eventory;



import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.lang.Validate;
import org.komea.eventory.utils.PluginUtils;
import org.komea.product.cep.api.ICEPConfiguration;
import org.komea.product.cep.api.ICEPEngine;
import org.komea.product.cep.api.IEventBridge;
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
    
    
    
    private static final Logger LOGGER      = LoggerFactory.getLogger("cep-engine");
    
    private ICEPConfiguration   cepConfiguration;
    private IEventBridge        eventBridge = null;
    
    
    private CEPEngineMode       mode        = CEPEngineMode.NOT_STARTED;
    
    private IQueryAdministrator queryAdministrator;
    
    
    
    /**
     * Builds the CEP Engine with the given configuration.
     * 
     * @param _cepConfiguration
     *            the cep configuration/
     */
    public CEPEngine() {
    
    
        cepConfiguration = new CEPConfiguration();
        
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.io.Closeable#close()
     */
    @Override
    public void close() throws IOException {
    
    
        try {
            queryAdministrator = null;
            eventBridge = null;
        } finally {
            mode = CEPEngineMode.DESTROYED;
        }
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPEngine#getConfiguration()
     */
    @Override
    public ICEPConfiguration getConfiguration() {
    
    
        return cepConfiguration;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPEngine#getQueryAdministration()
     */
    @Override
    public IQueryAdministrator getQueryAdministration() {
    
    
        return queryAdministrator;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPEngine#initialize()
     */
    @Override
    public void initialize(final ICEPConfiguration _configuration) throws IOException {
    
    
        Validate.notNull(_configuration);
        cepConfiguration = _configuration;
        if (isInitialized()) {
            close();
        }
        final int res = cepConfiguration.getNumberQueryListeners();
        LOGGER.debug("CEP Engine starts with {} query listeners", res);
        eventBridge = PluginUtils.getBridgeFactory().newBridge(cepConfiguration);
        
        queryAdministrator = new QueryAdministrator(eventBridge);
        mode = CEPEngineMode.RUNNING;
    }
    
    
    /**
     * Returns true if the engine is initialized.
     */
    public boolean isInitialized() {
    
    
        return mode == CEPEngineMode.RUNNING;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPEngine#pushEvent(org.komea.product.database.alert.Serializable)
     */
    @Override
    public void pushEvent(final Serializable _event) {
    
    
        if (!isInitialized()) { throw new RuntimeException(
                "The CEP engine has not been initialized, please invoke initialize() method"); }
        Validate.notNull(_event);
        
        eventBridge.notify(_event);
    }
    
}
