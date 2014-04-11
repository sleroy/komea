/**
 * 
 */

package org.komea.product.backend.service.esper;



import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.database.alert.Event;
import org.komea.product.database.alert.EventDtoBuilder;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Provider;
import org.mockito.Matchers;
import org.mockito.Mockito;



/**
 * This class is the unit test to check alerts may be pushed
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class EventPushServiceTest
{
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.esper.EventPushService#sendEvent(org.komea.product.database.alert.IEvent)}.
     */
    @Test 
    public final void testSendEvent() {
    
    
        final EventPushService alertPushService = new EventPushService();
        final IEventEngineService esperEngineMock = Mockito.mock(IEventEngineService.class);
        alertPushService.setEsperEngine(esperEngineMock);
        final IEventConversionAndValidationService mock =
                Mockito.mock(IEventConversionAndValidationService.class, Mockito.withSettings()
                        .verboseLogging());
        final Event value = new Event();
        value.setEventType(Mockito.mock(EventType.class));
        value.setProvider(Mockito.mock(Provider.class));
        Mockito.when(mock.convert(Matchers.any(EventSimpleDto.class))).thenReturn(value);
        alertPushService.setValidator(mock);
        alertPushService.sendEventDto(EventDtoBuilder.newAlert().message("DemoAlert").build());
        
        verify(esperEngineMock, Mockito.times(1)).sendEvent(Matchers.any(IEvent.class));
        
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.esper.EventPushService#sendEventWithoutValidation(org.komea.product.database.alert.IEvent)}.
     */
    @Test 
    public final void testSendEventWithoutValidation() {
    
    
        final EventPushService alertPushService = new EventPushService();
        final IEventEngineService esperEngineMock = Mockito.mock(IEventEngineService.class);
        alertPushService.setEsperEngine(esperEngineMock);
        final IEventConversionAndValidationService mock =
                Mockito.mock(IEventConversionAndValidationService.class, Mockito.withSettings()
                        .verboseLogging());
        final Event value = new Event();
        value.setEventType(Mockito.mock(EventType.class));
        value.setProvider(Mockito.mock(Provider.class));
        
        Mockito.when(mock.convert(Matchers.any(EventSimpleDto.class))).thenReturn(value);
        alertPushService.setValidator(mock);
        
        final EventSimpleDto event = EventDtoBuilder.newAlert().message("DemoAlert").build();
        event.setDate(null);
        alertPushService.sendEventDto(event);
        verify(esperEngineMock, Mockito.times(1)).sendEvent(Matchers.any(IEvent.class));
        
    }
    
}
