
package org.komea.connectors.git;


import java.io.IOException;
import java.util.List;

import org.komea.event.model.api.IBasicEvent;
import org.komea.event.model.api.IComplexEvent;
import org.komea.event.model.api.IFlatEvent;
import org.komea.event.storage.api.IEventStorage;

import com.google.common.collect.Lists;

public class BasicEventsListStorage implements IEventStorage
{
    
    List<IBasicEvent> events = Lists.newArrayList();
    
    @Override
    public void close() throws IOException {
    
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void storeComplexEvent(final IComplexEvent _event) {
    
        this.events.add(_event);
        
    }
    
    @Override
    public void storeEvent(final IBasicEvent _event) {
    
        this.events.add(_event);
    }
    
    @Override
    public void storeFlatEvent(final IFlatEvent _event) {
    
    }
    
    @Override
    public void storePojoAsEvent(final Object _pojo) {
    
    }
    
    
    
    public List<IBasicEvent> getEvents() {
    
        return this.events;
    }
}
