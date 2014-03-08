
package org.komea.product.backend.service.esper;



import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dto.EventSimpleDto;



/**
 */
public interface IEventConversionAndValidationService
{
    
    
    /**
     * Method convert.
     * 
     * @param _dto
     *            EventSimpleDto
     * @return IEvent
     */
    public IEvent convert(EventSimpleDto _dto);
    
    
    /**
     * Validate the vent
     * Method validate.
     * 
     * @param _event
     *            IEvent
     */
    public void validate(IEvent _event);
    
    
    /**
     * Validation
     * 
     * @param _object
     *            the object.
     */
    <T> void validateObject(T _object);
    
}
