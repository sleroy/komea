
package org.komea.product.backend.exceptions;



import org.komea.product.database.model.EventType;



public class AlreadyExistingEventTypeException extends RuntimeException
{
    
    
    private final EventType eventType;
    
    
    
    public AlreadyExistingEventTypeException(final EventType _eventType) {
    
    
        super("Event type " + _eventType.getName() + " is already registered");
        
        eventType = _eventType;
        
        
    }
    
    
    public EventType getEventType() {
    
    
        return eventType;
    }
    
}
