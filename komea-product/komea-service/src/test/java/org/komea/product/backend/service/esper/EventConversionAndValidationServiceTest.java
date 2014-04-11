/**
 * 
 */

package org.komea.product.backend.service.esper;



import org.junit.Test;
import org.komea.product.database.alert.IEvent;
import org.mockito.Mockito;



/**
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class EventConversionAndValidationServiceTest
{
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.esper.EventConversionAndValidationService#validate(org.komea.product.database.alert.IEvent)}
     * .
     */
    @Test @Ignore(expected = IllegalArgumentException.class)
    public final void testValidate() {
    
    
        final EventConversionAndValidationService alertValidationService =
                new EventConversionAndValidationService();
        alertValidationService.initialize();
        alertValidationService.validate(Mockito.mock(IEvent.class));
    }
    
}
