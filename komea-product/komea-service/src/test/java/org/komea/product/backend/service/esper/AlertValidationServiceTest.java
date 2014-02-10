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
public class AlertValidationServiceTest
{
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.esper.AlertValidationService#validate(org.komea.product.database.alert.IEvent)}.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testValidate() {
    
    
        final AlertValidationService alertValidationService = new AlertValidationService();
        alertValidationService.validate(Mockito.mock(IEvent.class));
    }
    
}
