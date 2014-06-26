/**
 *
 */

package org.komea.eventory.bridge;



import org.junit.Test;

import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */
public class BridgeFactoryTest
{


    /**
     * Test method for {@link org.komea.eventory.bridge.BridgeFactory#newBridge(org.komea.eventory.api.engine.ICEPConfiguration)}.
     */
    @Test
    public final void testNewBridge() throws Exception {


        assertTrue(new BridgeFactory().newBridge() instanceof MemoryBridge);
    }
}
