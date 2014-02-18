
package org.komea.product.backend.service.esper;



import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dto.EventSimpleDto;



public interface IEventConversionAndValidationService
{
    
    
    public IEvent convert(EventSimpleDto _dto);
    
    
    public void validate(IEvent _event);
    
}
