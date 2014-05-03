/**
 * 
 */

package org.komea.product.cep.cache;



import org.junit.Test;
import org.komea.eventory.CEPConfiguration;
import org.komea.eventory.bridge.MemoryBridge;

import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */
public class BridgeFactoryTest
{
    
    
    /**
     * Test method for {@link org.komea.product.cep.cache.BridgeFactory#newBridge(org.komea.eventory.api.engine.ICEPConfiguration)}.
     */
    @Test
    public final void testNewMemoryBridge() throws Exception {
    
    
        final BridgeFactory bridgeFactory = new BridgeFactory();
        bridgeFactory.setImplementation(MemoryBridge.class.getName());
        bridgeFactory.init();
        assertTrue(bridgeFactory.newBridge(new CEPConfiguration()) instanceof MemoryBridge);
        
    }
}
