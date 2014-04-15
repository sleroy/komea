/**
 * 
 */

package org.komea.product.cep.cache;



import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.komea.eventory.api.bridge.IEventBridge;
import org.komea.eventory.api.engine.ICEPConfiguration;
import org.komea.eventory.api.engine.ICEPQuery;
import org.komea.eventory.bridge.MemoryBridge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This class stores the memory bridge.
 * 
 * @author sleroy
 */
public class BackupMemoryBridge implements IEventBridge
{
    
    
    private static final Logger      LOGGER            = LoggerFactory
                                                               .getLogger(BackupMemoryBridge.class);
    
    
    private final List<Serializable> events            = new ArrayList<Serializable>();
    
    
    private int                      maxEvents;
    
    
    private IEventBridge             memoryEventBridge = null;
    
    
    private final File               storageFolder;
    
    
    
    /**
     * Backup memory bridge.
     */
    public BackupMemoryBridge(final ICEPConfiguration _configuration) {
    
    
        super();
        
        memoryEventBridge = new MemoryBridge(_configuration);
        try {
            maxEvents = Integer.parseInt(_configuration.getExtraProperties().get("max-events"));
        } catch (final Exception exception) {
            maxEvents = 100;
        }
        storageFolder = new File(_configuration.getStorageFolder(), "backupevents");
        storageFolder.mkdirs();
        LOGGER.info("Storage folder for backup events {}", storageFolder);
        
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
    public synchronized void notify(final Serializable _arg0) {
    
    
        events.add(_arg0);
        LOGGER.info("Number of events {}.", events.size());
        if (events.size() >= maxEvents) {
            
            
            try {
                final File outputFile =
                        new File(storageFolder, "events-" + new Date().getTime() + ".xml");
                LOGGER.warn("Flushing a set of events into {}", outputFile);
                XmlUtils.serializationXStream(events, outputFile);
                
            } catch (final IOException e) {
                
                e.printStackTrace();
            }
            
            events.clear();
        }
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
