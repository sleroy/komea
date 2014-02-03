
package org.komea.product.backend.service.esper;



import java.util.Date;

import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.database.alert.IAlert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class AlertPushService implements IAlertPushService
{
    
    
    @Autowired
    private IEsperEngine            esperEngine;
    
    
    @Autowired
    private IAlertValidationService validator;
    
    
    
    public AlertPushService() {
    
    
        super();
    }
    
    
    @Override
    public void sendEvent(final IAlert _alert) {
    
    
        validator.validate(_alert);
        sendEventWithoutValidation(_alert);
        
    }
    
    
    @Override
    public void sendEventWithoutValidation(final IAlert _alert) {
    
    
        if (_alert.getDate() == null) {
            _alert.setDate(new Date());
        }
        
        esperEngine.sendAlert(_alert);
        
        
    }
    
    
    public void setEsperEngine(final IEsperEngine _esperEngine) {
    
    
        esperEngine = _esperEngine;
    }
    
    
    public void setValidator(final IAlertValidationService _validator) {
    
    
        validator = _validator;
    }
    
}
