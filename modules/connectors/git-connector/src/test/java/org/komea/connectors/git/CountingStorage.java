package org.komea.connectors.git;

import java.io.IOException;

import org.komea.event.model.api.IBasicEvent;
import org.komea.event.model.api.IComplexEvent;
import org.komea.event.model.api.IFlatEvent;
import org.komea.event.storage.api.IEventStorage;

public class CountingStorage implements IEventStorage
{
    
    public int count;
    
    @Override
    public void close() throws IOException {
    
    }
    
    @Override
    public void storeComplexEvent(final IComplexEvent _event) {
        this.count++;
    }
    
    @Override
    public void storeEvent(final IBasicEvent _event) {
    
        this.count++;
        
    }
    
    @Override
    public void storeFlatEvent(final IFlatEvent _event) {
        this.count++;
    }
    
    @Override
    public void storePojoAsEvent(final Object _pojo) {
        this.count++;
    }
    
}