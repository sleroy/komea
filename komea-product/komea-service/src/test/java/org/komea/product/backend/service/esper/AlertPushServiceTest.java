/**
 * 
 */

package org.komea.product.backend.service.esper;



import static org.mockito.Mockito.verify;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.database.alert.EventDtoBuilder;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dto.EventSimpleDto;
import org.mockito.Matchers;
import org.mockito.Mockito;



/**
 * This class is the unit test to check alerts may be pushed
 * 
 * @author sleroy
 */
public class AlertPushServiceTest
{
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.esper.AlertPushService#sendEvent(org.komea.product.database.alert.IEvent)}.
     */
    @Test
    public final void testSendEvent() {
    
    
        final AlertPushService alertPushService = new AlertPushService();
        final IEsperEngine esperEngineMock = Mockito.mock(IEsperEngine.class);
        alertPushService.setEsperEngine(esperEngineMock);
        alertPushService.setValidator(Mockito.mock(IAlertValidationService.class));
        alertPushService.sendEventDto(EventDtoBuilder.newAlert().message("DemoAlert").build());
        verify(esperEngineMock, Mockito.times(1)).sendAlert(Matchers.any(IEvent.class));
        
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.esper.AlertPushService#sendEventWithoutValidation(org.komea.product.database.alert.IEvent)}.
     */
    @Test
    public final void testSendEventWithoutValidation() {
    
    
        final AlertPushService alertPushService = new AlertPushService();
        final IEsperEngine esperEngineMock = Mockito.mock(IEsperEngine.class);
        alertPushService.setEsperEngine(esperEngineMock);
        alertPushService.setValidator(Mockito.mock(IAlertValidationService.class));
        final EventSimpleDto event = EventDtoBuilder.newAlert().message("DemoAlert").build();
        event.setDate(null);
        alertPushService.sendEventDto(event);
        verify(esperEngineMock, Mockito.times(1)).sendAlert(Matchers.any(IEvent.class));
        Assert.assertNotNull(event.getDate());
    }
    
}
