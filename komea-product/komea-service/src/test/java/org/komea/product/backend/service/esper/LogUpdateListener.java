
package org.komea.product.backend.service.esper;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;



/**
 * Log Update Listener.
 * 
 * @author sleroy
 */
public class LogUpdateListener implements UpdateListener
{
    
    
    private final String        statementName;
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LogUpdateListener.class);
    
    
    
    public LogUpdateListener(final String _statementName) {
    
    
        statementName = _statementName;
        
        
    }
    
    
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
