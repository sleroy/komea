/**
 * 
 */

package org.komea.product.backend.esper.listeners;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceStateListener;

/**
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class EPServiceStateListener1 implements EPServiceStateListener
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EPServiceStateListener1.class);
    
    /*
     * (non-Javadoc)
     * @see
     * com.espertech.esper.client.EPServiceStateListener#onEPServiceDestroyRequested
     * (com.espertech.esper.client.EPServiceProvider)
     */
    
    /**
     * Method onEPServiceDestroyRequested.
     * @param arg0 EPServiceProvider
     * @see com.espertech.esper.client.EPServiceStateListener#onEPServiceDestroyRequested(EPServiceProvider)
     */
    @Override
    public void onEPServiceDestroyRequested(final EPServiceProvider arg0) {
    
        LOGGER.info("Esper Service provider destroyed " + arg0);
        
    }
    
    /*
     * (non-Javadoc)
     * @see
     * com.espertech.esper.client.EPServiceStateListener#onEPServiceInitialized
     * (com.espertech.esper.client.EPServiceProvider)
     */
    
    /**
     * Method onEPServiceInitialized.
     * @param arg0 EPServiceProvider
     * @see com.espertech.esper.client.EPServiceStateListener#onEPServiceInitialized(EPServiceProvider)
     */
    @Override
    public void onEPServiceInitialized(final EPServiceProvider arg0) {
    
        LOGGER.info("Esper Service provider initialized " + arg0);
        
    }
    
}
