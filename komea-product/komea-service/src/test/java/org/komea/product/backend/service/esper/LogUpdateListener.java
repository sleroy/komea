
package org.komea.product.backend.service.esper;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;



/**
 * Log Update Listener.
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class LogUpdateListener implements UpdateListener
{
    
    
    private final String        statementName;
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LogUpdateListener.class);
    
    
    
    /**
     * Constructor for LogUpdateListener.
     * @param _statementName String
     */
    public LogUpdateListener(final String _statementName) {
    
    
        statementName = _statementName;
        
        
    }
    
    
    /**
     * Method update.
     * @param _newEvents EventBean[]
     * @param _oldEvents EventBean[]
     * @see com.espertech.esper.client.UpdateListener#update(EventBean[], EventBean[])
     */
    @Override
    public void update(final EventBean[] _newEvents, final EventBean[] _oldEvents) {
    
    
        for (final EventBean bean : _newEvents) {
            LOGGER.info("[{}]: new events : {}", statementName, bean);
        }
        if (_oldEvents != null) {
            for (final EventBean bean : _oldEvents) {
                LOGGER.info("[{}]: old events : {}", statementName, bean);
            }
        }
        
    }
    
}
