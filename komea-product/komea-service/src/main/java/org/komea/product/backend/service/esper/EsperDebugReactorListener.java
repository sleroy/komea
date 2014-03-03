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
 * @version $Revision: 1.0 $
 */
public class EsperDebugReactorListener implements UpdateListener
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger("esper-debug");
    private IEsperEngine        esperEngine;
    
    
    
    /**
	 * 
	 */
    public EsperDebugReactorListener() {
    
    
        super();
    }
    
    
    /**
     * Method getEsperEngine.
     * 
     * @return IEsperEngine
     */
    public IEsperEngine getEsperEngine() {
    
    
        return esperEngine;
    }
    
    
    public void init() {
    
    
        LOGGER.debug("Initializing debug provider");
        final String expression = "select * from Event";
        LOGGER.debug("Esper component referenced : " + (esperEngine != null ? "Y" : "N"));
        LOGGER.debug("Esper Initialized : " + (esperEngine.getEsper() != null ? "Y" : "N"));
        esperEngine.getEsper().getEPAdministrator().createEPL(expression).addListener(this);
    }
    
    
    /**
     * Method setEsperEngine.
     * 
     * @param _esperEngine
     *            IEsperEngine
     */
    public void setEsperEngine(final IEsperEngine _esperEngine) {
    
    
        esperEngine = _esperEngine;
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * com.espertech.esper.client.UpdateListener#update(com.espertech.esper.
     * client.EventBean[], com.espertech.esper.client.EventBean[])
     */
    /**
     * Method update.
     * 
     * @param arg0
     *            EventBean[]
     * @param arg1
     *            EventBean[]
     * @see com.espertech.esper.client.UpdateListener#update(EventBean[], EventBean[])
     */
    @Override
    public void update(final EventBean[] arg0, final EventBean[] arg1) {
    
    
        LOGGER.debug("Event received {} ", arg0[0].getUnderlying());
    }
    
    
}
