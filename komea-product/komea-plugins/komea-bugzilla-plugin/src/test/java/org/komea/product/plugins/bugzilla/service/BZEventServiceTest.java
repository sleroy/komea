/**
 * 
 */

package org.komea.product.plugins.bugzilla.service;



import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.esper.IEventPushService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class BZEventServiceTest
{
    
    
    @InjectMocks
    private BZEventService    bZEventService;
    @Mock
    private IEventPushService service;
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.bugzilla.service.BZEventService#sendAllEvents(org.komea.product.plugins.bugzilla.core.BugsCalculator, java.lang.String, org.komea.product.plugins.bugzilla.model.BZServerConfiguration)}
     * .
     */
    @Test
    @Ignore
    public final void testSendAllEvents() throws Exception {
    
    
        // TODO
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
}
