
package org.komea.connectors.git;


import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.komea.event.model.IFlatEvent;
import org.komea.event.model.beans.AbstractEvent;
import org.komea.event.model.beans.BasicEvent;
import org.komea.event.model.beans.ComplexEvent;
import org.komea.event.storage.IEventStorage;

public class CountingStorage implements IEventStorage
{
    
    public int count;
    
    @Override
    public void storeBasicEvent(final BasicEvent _event) {
    
        this.count++;
        
    }
    
    @Override
    public void storeComplexEvent(final ComplexEvent _event) {
    
        this.count++;
        
    }
    
    @Override
    public void storeEvent(final AbstractEvent _event) {
    
        this.count++;
        
    }
    
    @Override
    public void storeFlatEvent(final IFlatEvent _event) {
    
        this.count++;
    }
    
    @Override
    public void storeMap(final Map<String, Serializable> _fieldMap) {
    
        this.count++;
    }
    
    @Override
    public void storePojo(final Object _pojo) {
    
        this.count++;
    }

    @Override
    public void close() throws IOException {
    
        // TODO Auto-generated method stub
        
    }
    
}
