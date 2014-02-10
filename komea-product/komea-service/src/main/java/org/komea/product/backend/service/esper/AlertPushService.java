
package org.komea.product.backend.service.esper;



import java.util.Date;

import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dto.EventSimpleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class AlertPushService implements IEventPushService
{
    
    
    @Autowired
    private IEsperEngine            esperEngine;
    
    
    @Autowired
    private IAlertValidationService validator;
    
    
    
    public AlertPushService() {
    
    
        super();
    }
    
    
    @Override
    public void sendEvent(final IEvent _event) {
    
    
        validator.validate(_event);
        sendEventWithoutValidation(_event);
        
    }
    
    
    @Override
    public void sendEventDto(final EventSimpleDto _dto) {
    
    
        final IEvent convert = validator.convert(_dto);
        sendEvent(convert);
        
    }
    
    
    public void sendEventWithoutValidation(final IEvent _event) {
    
    
        if (_event.getDate() == null) {
            _event.setDate(new Date());
        }
        
        esperEngine.sendAlert(_event);
        
        
    }
    
    
    public void setEsperEngine(final IEsperEngine _esperEngine) {
    
    
        esperEngine = _esperEngine;
    }
    
    
    public void setValidator(final IAlertValidationService _validator) {
    
    
        validator = _validator;
    }
    
}
