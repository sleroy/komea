/**
 *
 */

package org.komea.eventory;



import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.lang3.Validate;
import org.komea.eventory.api.bridge.IEventBridge;
import org.komea.eventory.api.engine.ICEPEngine;
import org.komea.eventory.api.engine.IQueryAdministrator;
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

    private IEventBridge        eventBridge = null;


    private CEPEngineMode       mode        = CEPEngineMode.NOT_STARTED;

    private IQueryAdministrator queryAdministrator;



    /**
     * Builds the CEP Engine with the given configuration.
     */
    public CEPEngine() {
    
    
        super();
        
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
    public void initialize() throws IOException {


        Validate.notNull(CEPConfiguration.getInstance(), "no configuration provided");
        Validate.notNull(CEPConfiguration.getInstance().getBridgeFactory(), "no bridge factory");
        Validate.notNull(CEPConfiguration.getInstance().getCacheStorageFactory(),
                "no cache storage factory");
        Validate.notNull(CEPConfiguration.getInstance().getStorageFolder(), "no storage folder");
        if (isInitialized()) {
            close();
        }
        final int res = CEPConfiguration.getInstance().getNumberQueryListeners();
        LOGGER.debug("CEP Engine starts with {} query listeners", res);
        eventBridge = CEPConfiguration.getInstance().getBridgeFactory().newBridge();

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


        if (!isInitialized()) {
            throw new RuntimeException(
                    "The CEP engine has not been initialized, please invoke initialize() method");
        }
        Validate.notNull(_event, "null event provided");

        eventBridge.notify(_event);
    }

}
