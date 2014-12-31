
package org.komea.connectors.git;


import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.komea.event.model.IFlatEvent;
import org.komea.event.model.beans.AbstractEvent;
import org.komea.event.model.beans.BasicEvent;
import org.komea.event.model.beans.ComplexEvent;
import org.komea.event.storage.IEventStorage;

import com.google.common.collect.Lists;

public class BasicEventsListStorage implements IEventStorage
{
    
    List<AbstractEvent> events = Lists.newArrayList();
    
    @Override
    public void close() throws IOException {
    
    }
    
    @Override
    public void storeBasicEvent(final BasicEvent _event) {
    
        this.events.add(_event);
        
    }
    
    @Override
    public void storeComplexEvent(final ComplexEvent _event) {
    
        this.events.add(_event);
        
    }
    
    @Override
    public void storeEvent(final AbstractEvent _event) {
    
        this.events.add(_event);
        
    }
    
    @Override
    public void storeFlatEvent(final IFlatEvent _event) {
    
    }
    
    @Override
    public void storeMap(final Map<String, Serializable> _fieldMap) {
    
    }
    
    @Override
    public void storePojo(final Object _pojo) {
    
    }
    
    
    public List<AbstractEvent> getEvents() {
    
        return this.events;
    }

    @Override
    public void clearEventsOfType(final String _eventType) {
    
        
        
    }

    @Override
    public void declareEventType(final String type) {
    
        // TODO Auto-generated method stub
        
    }
    
}
