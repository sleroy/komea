/**
 * 
 */

package org.komea.product.backend.esper.listeners;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPStatementStateListener;

/**
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class EPStatementStateListener1 implements EPStatementStateListener
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EPStatementStateListener1.class);
    
    /*
     * (non-Javadoc)
     * @see
     * com.espertech.esper.client.EPStatementStateListener#onStatementCreate
     * (com.espertech.esper.client.EPServiceProvider,
     * com.espertech.esper.client.EPStatement)
     */
    
    /**
     * Method onStatementCreate.
     * @param arg0 EPServiceProvider
     * @param arg1 EPStatement
     * @see com.espertech.esper.client.EPStatementStateListener#onStatementCreate(EPServiceProvider, EPStatement)
     */
    @Override
    public void onStatementCreate(final EPServiceProvider arg0, final EPStatement arg1) {
    
        LOGGER.trace("onStatementCreate " + arg0 + " : " + arg1);
        
    }
    
    /*
     * (non-Javadoc)
     * @see
     * com.espertech.esper.client.EPStatementStateListener#onStatementStateChange
     * (com.espertech.esper.client.EPServiceProvider,
     * com.espertech.esper.client.EPStatement)
     */
    
    /**
     * Method onStatementStateChange.
     * @param arg0 EPServiceProvider
     * @param arg1 EPStatement
     * @see com.espertech.esper.client.EPStatementStateListener#onStatementStateChange(EPServiceProvider, EPStatement)
     */
    @Override
    public void onStatementStateChange(final EPServiceProvider arg0, final EPStatement arg1) {
    
        LOGGER.trace("onStatementStateChange " + arg0 + " : " + arg1);
        
    }
    
}
