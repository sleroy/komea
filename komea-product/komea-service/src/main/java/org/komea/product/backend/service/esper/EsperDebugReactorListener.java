/**
 * 
 */

package org.komea.product.backend.service.esper;



import org.komea.product.backend.api.IEsperEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;



/**
 * This type defines the debug reactor of esper.
 * 
 * @author sleroy
 */
public class EsperDebugReactorListener implements UpdateListener
{
    
    
    private IEsperEngine        esperEngine;
    private static final Logger LOGGER = LoggerFactory.getLogger(EsperDebugReactorListener.class);
    
    
    
    /**
	 * 
	 */
    public EsperDebugReactorListener() {
    
    
        super();
    }
    
    
    public IEsperEngine getEsperEngine() {
    
    
        return esperEngine;
    }
    
    
    public void init() {
    
    
        LOGGER.info("Initializing debug provider");
        final String expression = "select * from Event";
        LOGGER.info("Esper component referenced : " + (esperEngine != null ? "Y" : "N"));
        LOGGER.info("Esper Initialized : " + (esperEngine.getEsper() != null ? "Y" : "N"));
        esperEngine.getEsper().getEPAdministrator().createEPL(expression).addListener(this);
    }
    
    
    public void setEsperEngine(final IEsperEngine _esperEngine) {
    
    
        esperEngine = _esperEngine;
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * com.espertech.esper.client.UpdateListener#update(com.espertech.esper.
     * client.EventBean[], com.espertech.esper.client.EventBean[])
     */
    @Override
    public void update(final EventBean[] arg0, final EventBean[] arg1) {
    
    
        LOGGER.debug("Event received {} ", arg0[0].getUnderlying());
    }
    
    
}
