/**
 * 
 */

package org.komea.eventory.bridge;



import org.junit.Test;
import org.komea.product.cep.api.ICEPConfiguration;

import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.mock;



/**
 * @author sleroy
 */
public class BridgeFactoryTest
{
    
    
    /**
     * Test method for {@link org.komea.eventory.bridge.BridgeFactory#newBridge(org.komea.product.cep.api.ICEPConfiguration)}.
     */
    @Test
    public final void testNewBridge() throws Exception {
    
    
        assertTrue(new BridgeFactory().newBridge(mock(ICEPConfiguration.class)) instanceof MemoryBridge);
    }
}
