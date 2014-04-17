/**
 * 
 */

package org.komea.product.backend.service.esper;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.eventory.api.bridge.IEventBridgeFactory;
import org.komea.eventory.api.cache.ICacheStorageFactory;
import org.komea.eventory.api.engine.ICEPEngine;
import org.komea.product.backend.service.fs.IKomeaFS;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class EventEngineServiceTest
{
    
    
    @Mock
    private ICacheStorageFactory cacheStorageFactory;
    
    
    @Mock
    private ICEPEngine           cepEngine;
    
    
    @Mock
    private IEventBridgeFactory  eventBridgeFactory;
    
    
    @Mock
    private IKomeaFS             komeaFS;
    @InjectMocks
    private EventEngineService   eventEngineService;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.esper.EventEngineService#createOrUpdateQuery(org.komea.product.backend.api.IQueryDefinition)}.
     */
    @Test
    public final void testCreateOrUpdateQuery() throws Exception {
    
    
        // TODO
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.esper.EventEngineService#createQuery(org.komea.product.backend.api.IQueryDefinition)}.
     */
    @Test
    public final void testCreateQuery() throws Exception {
    
    
        // TODO
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.esper.EventEngineService#existQuery(java.lang.String)}.
     */
    @Test
    public final void testExistQuery() throws Exception {
    
    
        // TODO
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.esper.EventEngineService#sendEvent(org.komea.product.database.alert.IEvent)}.
     */
    @Test
    public final void testSendEvent() throws Exception {
    
    
        // TODO
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
}
