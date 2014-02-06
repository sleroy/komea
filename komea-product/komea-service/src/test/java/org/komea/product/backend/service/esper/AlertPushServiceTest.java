/**
 * 
 */

package org.komea.product.backend.service.esper;



import static org.mockito.Mockito.verify;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.database.alert.Alert;
import org.komea.product.database.alert.AlertBuilder;
import org.komea.product.database.alert.IAlert;
import org.komea.product.database.alert.enums.Criticity;
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
     * Test method for {@link org.komea.product.backend.service.esper.AlertPushService#sendEvent(org.komea.product.database.alert.IAlert)}.
     */
    @Test
    public final void testSendEvent() {
    
    
        final AlertPushService alertPushService = new AlertPushService();
        final IEsperEngine esperEngineMock = Mockito.mock(IEsperEngine.class);
        alertPushService.setEsperEngine(esperEngineMock);
        alertPushService.setValidator(Mockito.mock(IAlertValidationService.class));
        alertPushService.sendEvent(AlertBuilder.newAlert().category("PLUGIN_DEMO")
                .criticity(Criticity.CRITICAL).message("DemoAlert").build());
        verify(esperEngineMock, Mockito.times(1)).sendAlert(Matchers.any(IAlert.class));
        
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.esper.AlertPushService#sendEventWithoutValidation(org.komea.product.database.alert.IAlert)}.
     */
    @Test
    public final void testSendEventWithoutValidation() {
    
    
        final AlertPushService alertPushService = new AlertPushService();
        final IEsperEngine esperEngineMock = Mockito.mock(IEsperEngine.class);
        alertPushService.setEsperEngine(esperEngineMock);
        alertPushService.setValidator(Mockito.mock(IAlertValidationService.class));
        final Alert alert =
                AlertBuilder.newAlert().category("PLUGIN_DEMO").criticity(Criticity.CRITICAL)
                        .message("DemoAlert").build();
        alert.setDate(null);
        alertPushService.sendEvent(alert);
        verify(esperEngineMock, Mockito.times(1)).sendAlert(Matchers.any(IAlert.class));
        Assert.assertNotNull(alert.getDate());
    }
    
}
