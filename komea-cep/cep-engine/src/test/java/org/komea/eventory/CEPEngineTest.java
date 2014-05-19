/**
 * 
 */

package org.komea.eventory;



import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.komea.eventory.api.bridge.IEventBridge;
import org.komea.eventory.api.bridge.IEventBridgeFactory;
import org.komea.eventory.api.cache.ICacheConfiguration;
import org.komea.eventory.api.cache.ICacheStorage;
import org.komea.eventory.api.cache.ICacheStorageFactory;
import org.komea.eventory.api.engine.ICEPConfiguration;
import org.komea.eventory.cache.CacheStorageFactory;
import org.komea.eventory.utils.PluginUtils;
import org.mockito.Matchers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



/**
 * @author sleroy
 */
public class CEPEngineTest
{
    
    
    private static IEventBridgeFactory eventBridge;
    
    
    
    public static void buildFakeBridge() {
    
    
        eventBridge = mock(IEventBridgeFactory.class);
        when(eventBridge.newBridge(Matchers.any(ICEPConfiguration.class))).thenReturn(
                mock(IEventBridge.class));
        
    }
    
    
    /**
     * 
     */
    public static void buildFakeCache() {
    
    
        final ICacheStorageFactory cacheStorageFactory = mock(ICacheStorageFactory.class);
        when(cacheStorageFactory.newCacheStorage(Matchers.any(ICacheConfiguration.class)))
                .thenReturn(mock(ICacheStorage.class));
        PluginUtils.setCacheStorageFactory(cacheStorageFactory);
        
    }
    
    
    public void initializeConfiguration(final CEPEngine cepEngine) throws IOException {
    
    
        final CEPConfiguration configuration = new CEPConfiguration();
        configuration.setBridgeFactory(eventBridge);
        configuration.setCacheStorageFactory(new CacheStorageFactory());
        
        configuration.setNumberQueryListeners(1);
        configuration.setStorageFolder(new File("target"));
        cepEngine.initialize(configuration);
    }
    
    
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
        initializeConfiguration(cepEngine);
        
        cepEngine.close();
    }
    
    
    /**
     * Test method for {@link org.komea.eventory.CEPEngine#getQueryAdministration()}.
     */
    @Test
    public final void testGetQueryAdministration() throws Exception {
    
    
        buildFakeBridge();
        
        final CEPEngine cepEngine = new CEPEngine();
        Assert.assertNull("QueryAdmin is not initialized at begin",
                cepEngine.getQueryAdministration());
        initializeConfiguration(cepEngine);
        Assert.assertNotNull("QueryAdmin must be initialized", cepEngine.getQueryAdministration());
        cepEngine.close();
        
    }
    
    
    /**
     * Test method for {@link org.komea.eventory.CEPEngine#initialize(org.komea.eventory.api.engine.ICEPConfiguration)}.
     */
    @Test
    public final void testInitialize() throws Exception {
    
    
        final CEPEngine cepEngine = new CEPEngine();
        initializeConfiguration(cepEngine);
        Assert.assertTrue("Engine should be initialized", cepEngine.isInitialized());
        initializeConfiguration(cepEngine);
        Assert.assertTrue("Engine should be initialized again", cepEngine.isInitialized());
        cepEngine.close();
        
    }
    
    
    /**
     * Test method for {@link org.komea.eventory.CEPEngine#pushEvent(java.io.Serializable)}.
     */
    @Test
    public final void testPushEventInitialized() throws Exception {
    
    
        final CEPEngine cepEngine = new CEPEngine();
        initializeConfiguration(cepEngine);
        cepEngine.pushEvent(new String());
        
        cepEngine.close();
        
    }
    
    
    /**
     * Test method for {@link org.komea.eventory.CEPEngine#pushEvent(java.io.Serializable)}.
     */
    @Test(expected = RuntimeException.class)
    public final void testPushEventWithClosedEngine() throws Exception {
    
    
        final CEPEngine cepEngine = new CEPEngine();
        
        cepEngine.close();
        cepEngine.pushEvent(new String());
        
    }
    
    
    /**
     * Test method for {@link org.komea.eventory.CEPEngine#pushEvent(java.io.Serializable)}.
     */
    @Test(expected = Exception.class)
    public final void testPushEventWithoutInitialization() throws Exception {
    
    
        final CEPEngine cepEngine = new CEPEngine();
        cepEngine.pushEvent(new String());
        
        cepEngine.close();
        
    }
    
    
}
