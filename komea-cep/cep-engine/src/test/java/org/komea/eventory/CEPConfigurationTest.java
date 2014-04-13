/**
 * 
 */

package org.komea.eventory;



import org.junit.Assert;
import org.junit.Test;
import org.komea.eventory.CEPConfiguration;
import org.komea.eventory.api.engine.RunningMode;

import static org.junit.Assert.assertNotNull;



/**
 * @author sleroy
 */
public class CEPConfigurationTest
{
    
    
    /**
     * Test method for {@link org.komea.eventory.CEPConfiguration#getMode()}.
     */
    @Test
    public final void testGetMode() throws Exception {
    
    
        final CEPConfiguration configuration = new CEPConfiguration();
        configuration.setMode(RunningMode.AGILE);
        Assert.assertEquals(RunningMode.AGILE, configuration.getMode());
    }
    
    
    /**
     * Test method for {@link org.komea.eventory.CEPConfiguration#getNumberQueryListeners()}.
     */
    @Test
    public final void testGetNumberQueryListeners() throws Exception {
    
    
        final CEPConfiguration configuration = new CEPConfiguration();
        configuration.setNumberQueryListeners(2);
        Assert.assertEquals(2, configuration.getNumberQueryListeners());
    }
    
    
    /**
     * Test method for {@link org.komea.eventory.CEPConfiguration#toString()}.
     */
    @Test
    public void testToString() throws Exception {
    
    
        assertNotNull(new CEPConfiguration().toString());
    }
    
    
}
