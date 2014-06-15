/**
 *
 */

package org.komea.eventory.cache;



import java.io.Serializable;

import org.komea.eventory.api.bridge.IEventBridge;
import org.komea.eventory.api.bridge.IEventBridgeFactory;
import org.komea.eventory.api.engine.IQuery;



/**
 * @author sleroy
 */
public class EventBridgeFactory implements IEventBridgeFactory
{


    public static class EventBridge implements IEventBridge
    {
        
        
        /*
         * (non-Javadoc)
         * @see org.komea.eventory.api.bridge.IEventBridge#getQuery(java.lang.String)
         */
        @Override
        public IQuery getQuery(final String _query) {


            // TODO Auto-generated method stub
            return null;
        }
        
        
        /*
         * (non-Javadoc)
         * @see org.komea.eventory.api.engine.ICEPEventListener#notify(java.io.Serializable)
         */
        @Override
        public void notify(final Serializable _event) {


            // TODO Auto-generated method stub

        }
        
        
        /*
         * (non-Javadoc)
         * @see org.komea.eventory.api.bridge.IEventBridge#registerQuery(java.lang.String, org.komea.eventory.api.engine.IQuery)
         */
        @Override
        public void registerQuery(final String _queryName, final IQuery _query) {


            // TODO Auto-generated method stub

        }
        
        
        /*
         * (non-Javadoc)
         * @see org.komea.eventory.api.bridge.IEventBridge#removeQuery(java.lang.String)
         */
        @Override
        public void removeQuery(final String _queryName) {


            // TODO Auto-generated method stub

        }

    }
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.bridge.IEventBridgeFactory#newBridge()
     */
    @Override
    public IEventBridge newBridge() {
    
    
        return new EventBridge();
    }
}
