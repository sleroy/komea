/**
 * 
 */

package org.komea.product.cep;



import org.junit.Assert;
import org.junit.Test;
import org.komea.product.cep.api.RunningMode;



/**
 * @author sleroy
 */
public class CEPConfigurationTest
{
    
    
    /**
     * Test method for {@link org.komea.product.cep.CEPConfiguration#getMode()}.
     */
    @Test 
    public final void testGetMode() throws Exception {
    
    
        final CEPConfiguration configuration = new CEPConfiguration();
        configuration.setMode(RunningMode.AGILE);
        Assert.assertEquals(RunningMode.AGILE, configuration.getMode());
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.CEPConfiguration#getNumberQueryListeners()}.
     */
    @Test 
    public final void testGetNumberQueryListeners() throws Exception {
    
    
        final CEPConfiguration configuration = new CEPConfiguration();
        configuration.setNumberQueryListeners(2);
        Assert.assertEquals(2, configuration.getNumberQueryListeners());
    }
    
    
}
