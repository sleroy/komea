/**
 * 
 */

package org.komea.product.backend.service.esper;



import org.junit.Test;
import org.komea.product.database.alert.IEvent;
import org.mockito.Mockito;



/**
 * @author sleroy
 */
public class EventConvertionAndValidationServiceTest
{
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.esper.EventConvertionAndValidationService#validate(org.komea.product.database.alert.IEvent)}.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testValidate() {
    
    
        final EventConvertionAndValidationService alertValidationService = new EventConvertionAndValidationService();
        alertValidationService.validate(Mockito.mock(IEvent.class));
    }
    
}
