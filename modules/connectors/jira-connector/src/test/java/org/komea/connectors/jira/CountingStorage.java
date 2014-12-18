
package org.komea.connectors.jira;


import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.komea.event.model.IFlatEvent;
import org.komea.event.model.beans.AbstractEvent;
import org.komea.event.model.beans.BasicEvent;
import org.komea.event.model.beans.ComplexEvent;
import org.komea.event.storage.IEventStorage;

import com.google.common.collect.Maps;

public class CountingStorage implements IEventStorage
{
    
    public Map<String, Integer> counters;
    
    public CountingStorage() {
    
        this.counters = Maps.newHashMap();
    }
    @Override
    public void storeBasicEvent(final BasicEvent _event) {
    
        increment(_event.getEventType());
        
    }
    private void increment(final String type) {
    
        Integer current = this.counters.get(type);
        if (current == null) {
            current = 0;
        }
        this.counters.put(type, current + 1);
    }
    @Override
    public void storeComplexEvent(final ComplexEvent _event) {
    
        increment(_event.getEventType());
        
    }
    
    @Override
    public void storeEvent(final AbstractEvent _event) {
    
        increment(_event.getEventType());
        
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
    
    @Override
    public void close() throws IOException {
    
        // TODO Auto-generated method stub
        
    }
    @Override
    public void clearEventsOfType(final String _eventType) {
    
        // TODO Auto-generated method stub
        
    }
    
}
