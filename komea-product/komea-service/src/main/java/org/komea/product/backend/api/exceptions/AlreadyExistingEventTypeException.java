
package org.komea.product.backend.api.exceptions;



import org.komea.product.database.model.EventType;



/**
 */
public class AlreadyExistingEventTypeException extends RuntimeException
{
    
    
    private final EventType eventType;
    
    
    
    /**
     * Constructor for AlreadyExistingEventTypeException.
     * @param _eventType EventType
     */
    public AlreadyExistingEventTypeException(final EventType _eventType) {
    
    
        super("Event type " + _eventType.getName() + " is already registered");
        
        eventType = _eventType;
        
        
    }
    
    
    /**
     * Method getEventType.
     * @return EventType
     */
    public EventType getEventType() {
    
    
        return eventType;
    }
    
}
