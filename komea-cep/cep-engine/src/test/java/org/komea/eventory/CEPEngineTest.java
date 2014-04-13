/**
 * 
 */

package org.komea.eventory;



import org.junit.Assert;
import org.junit.Test;
import org.komea.eventory.api.bridge.IEventBridgeFactory;
import org.komea.eventory.utils.PluginUtils;
import org.komea.product.cep.api.ICEPConfiguration;
import org.komea.product.cep.api.IEventBridge;
import org.mockito.Matchers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



/**
 * @author sleroy
 */
public class CEPEngineTest
{
    
    
    /**
     * Test method for {@link org.komea.eventory.CEPEngine#CEPEngine()}.
     */
    @Test
    public final void testCEPEngine() throws Exception {
    
    
        final CEPEngine cepEngine = new CEPEngine();
        Assert.assertFalse("CepEngine is not initialized", cepEngine.isInitialized());
        cepEngine.close();
    }
    
    
    /**
     * Test method for {@link org.komea.eventory.CEPEngine#close()}.
     */
    @Test
    public final void testClose() throws Exception {
    
    
        final CEPEngine cepEngine = new CEPEngine();
        Assert.assertFalse("CepEngine is not initialized", cepEngine.isInitialized());
        cepEngine.close();
        cepEngine.close();// sucessing close() must not crash
        cepEngine.close();
        
    }
    
    
    /**
     * Test method for {@link org.komea.eventory.CEPEngine#getConfiguration()}.
     */
    @Test
    public final void testGetConfiguration() throws Exception {
    
    
        final CEPEngine cepEngine = new CEPEngine();
        Assert.assertNotNull("A configuration must be provided on default initialization",
                cepEngine.getConfiguration());
        final CEPConfiguration configuration = new CEPConfiguration();
        cepEngine.initialize(configuration);
        Assert.assertEquals("Configuration must be initialized with the parameter", configuration,
                cepEngine.getConfiguration());
        cepEngine.close();
    }
    
    
    /**
     * Test method for {@link org.komea.eventory.CEPEngine#getQueryAdministration()}.
     */
    @Test
    public final void testGetQueryAdministration() throws Exception {
    
    
        final IEventBridgeFactory mock = mock(IEventBridgeFactory.class);
        when(mock.newBridge(Matchers.any(ICEPConfiguration.class))).thenReturn(
                mock(IEventBridge.class));
        PluginUtils.setBridgeFactory(mock);
        
        final CEPEngine cepEngine = new CEPEngine();
        Assert.assertNull("QueryAdmin is not initialized at begin",
                cepEngine.getQueryAdministration());
        cepEngine.initialize(new CEPConfiguration());
        Assert.assertNotNull("QueryAdmin must be initialized", cepEngine.getQueryAdministration());
        cepEngine.close();
        
    }
    
    
    /**
     * Test method for {@link org.komea.eventory.CEPEngine#initialize(org.komea.eventory.api.ICEPConfiguration)}.
     */
    @Test
    public final void testInitialize() throws Exception {
    
    
        final CEPEngine cepEngine = new CEPEngine();
        cepEngine.initialize(new CEPConfiguration()); // Initialisation
        Assert.assertTrue("Engine should be initialized", cepEngine.isInitialized());
        cepEngine.initialize(new CEPConfiguration());
        Assert.assertTrue("Engine should be initialized again", cepEngine.isInitialized());
        cepEngine.close();
        
    }
    
    
    /**
     * Test method for {@link org.komea.eventory.CEPEngine#pushEvent(java.io.Serializable)}.
     */
    @Test
    public final void testPushEventInitialized() throws Exception {
    
    
        final CEPEngine cepEngine = new CEPEngine();
        cepEngine.initialize(new CEPConfiguration());
        cepEngine.pushEvent(new String());
        
        cepEngine.close();
        
    }
    
    
    /**
     * Test method for {@link org.komea.eventory.CEPEngine#pushEvent(java.io.Serializable)}.
     */
    @Test(
        expected = RuntimeException.class)
    public final void testPushEventWithClosedEngine() throws Exception {
    
    
        final CEPEngine cepEngine = new CEPEngine();
        
        cepEngine.close();
        cepEngine.pushEvent(new String());
        
    }
    
    
    /**
     * Test method for {@link org.komea.eventory.CEPEngine#pushEvent(java.io.Serializable)}.
     */
    @Test(
        expected = Exception.class)
    public final void testPushEventWithoutInitialization() throws Exception {
    
    
        final CEPEngine cepEngine = new CEPEngine();
        cepEngine.pushEvent(new String());
        
        cepEngine.close();
        
    }
    
    
}
