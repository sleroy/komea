/**
 * 
 */

package org.komea.product.cep.cache;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.komea.eventory.api.bridge.IEventBridge;
import org.komea.eventory.api.engine.ICEPConfiguration;
import org.komea.eventory.api.engine.ICEPQuery;
import org.komea.eventory.bridge.MemoryBridge;



/**
 * This class stores the memory bridge.
 * 
 * @author sleroy
 */
public class BackupMemoryBridge implements IEventBridge
{
    
    
    private final List<Serializable> events            = new ArrayList<Serializable>();
    
    
    private IEventBridge             memoryEventBridge = null;
    
    
    
    /**
     * Backup memory bridge.
     */
    public BackupMemoryBridge(final ICEPConfiguration _configuration) {
    
    
        super();
        
        memoryEventBridge = new MemoryBridge(_configuration);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.bridge.IEventBridge#getQuery(java.lang.String)
     */
    @Override
    public ICEPQuery getQuery(final String _arg0) {
    
    
        return memoryEventBridge.getQuery(_arg0);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.engine.ICEPEventListener#notify(java.io.Serializable)
     */
    @Override
    public void notify(final Serializable _arg0) {
    
    
        memoryEventBridge.notify(_arg0);
        
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.bridge.IEventBridge#registerQuery(java.lang.String, org.komea.eventory.api.engine.ICEPQuery)
     */
    @Override
    public void registerQuery(final String _arg0, final ICEPQuery _arg1) {
    
    
        memoryEventBridge.registerQuery(_arg0, _arg1);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.bridge.IEventBridge#removeQuery(java.lang.String)
     */
    @Override
    public void removeQuery(final String _arg0) {
    
    
        memoryEventBridge.removeQuery(_arg0);
        
    }
}
