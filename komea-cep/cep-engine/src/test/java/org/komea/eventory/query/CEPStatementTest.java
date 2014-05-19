
package org.komea.eventory.query;



import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.komea.eventory.api.cache.ICacheStorage;
import org.komea.eventory.api.engine.ICEPEventStorage;
import org.mockito.Mockito;



public class CEPStatementTest
{
    
    
    @Test
        public final void testAddStorage() throws Exception {
        
        
            final CEPStatement cepStatement = new CEPStatement();
            final ICEPEventStorage mock = Mockito.mock(ICEPEventStorage.class);
            cepStatement.addStorage(mock);
            Assert.assertTrue("storage should be registered",
                    cepStatement.getEventStorages().contains(mock));
            Assert.assertEquals("should contains only one storage", 1, cepStatement.getEventStorages()
                    .size());
            
        }
    
    
    @Test
    public final void testGetAggregateView() throws Exception {
    
    
        final CEPStatement cepStatement = new CEPStatement();
        final ICEPEventStorage storage = Mockito.mock(ICEPEventStorage.class);
        final ICEPEventStorage storage2 = Mockito.mock(ICEPEventStorage.class);
        
        final ICacheStorage cacheStorage = Mockito.mock(ICacheStorage.class);
        final ICacheStorage cacheStorage2 = Mockito.mock(ICacheStorage.class);
        Mockito.when(storage.getCache()).thenReturn(cacheStorage);
        Mockito.when(storage2.getCache()).thenReturn(cacheStorage2);
        Mockito.when(cacheStorage.getAllValues()).thenReturn(Collections.singletonList("truc"));
        Mockito.when(cacheStorage2.getAllValues()).thenReturn(Collections.singletonList("truc2"));
        
        cepStatement.addStorage(storage);
        cepStatement.addStorage(storage2);
        final List aggregateView = cepStatement.getAggregateView();
        Mockito.verify(storage, Mockito.atLeastOnce()).getCache();
        Mockito.verify(storage2, Mockito.atLeastOnce()).getCache();
        Mockito.verify(cacheStorage, Mockito.atLeastOnce()).getAllValues();
        Mockito.verify(cacheStorage2, Mockito.atLeastOnce()).getAllValues();
        Assert.assertTrue("should contains truc", aggregateView.contains("truc"));
        Assert.assertTrue("should contains truc2", aggregateView.contains("truc2"));
    }
    
    
    @Test
    public final void testGetDefaultStorage() throws Exception {
    
    
        final CEPStatement cepStatement = new CEPStatement();
        final ICEPEventStorage storage = Mockito.mock(ICEPEventStorage.class);
        
        final ICacheStorage cacheStorage = Mockito.mock(ICacheStorage.class);
        Mockito.when(storage.getCache()).thenReturn(cacheStorage);
        
        cepStatement.addStorage(storage);
        cepStatement.getDefaultStorage();
        Mockito.verify(storage, Mockito.atLeastOnce()).getCache();
        Mockito.verify(cacheStorage, Mockito.atLeastOnce()).getAllValues();
        
    }
    
    
    @Test
    public final void testGetEventStorage() throws Exception {
    
    
        final CEPStatement cepStatement = new CEPStatement();
        final ICEPEventStorage storage = Mockito.mock(ICEPEventStorage.class);
        
        Mockito.when(storage.getFilterName()).thenReturn("default");
        cepStatement.addStorage(storage);
        Assert.assertNotNull("Should contains event storage default",
                cepStatement.getEventStorage("default"));
        
        
    }
    
    
    @Test
    public final void testGetEventStorages() throws Exception {
    
    
        final CEPStatement cepStatement = new CEPStatement();
        final ICEPEventStorage storage = Mockito.mock(ICEPEventStorage.class);
        
        
        cepStatement.addStorage(storage);
        Assert.assertEquals("Should contains event storage", 1, cepStatement.getEventStorages()
                .size());
        Assert.assertEquals(storage, cepStatement.getEventStorages().get(0));
    }
    
    
    @Test
    public final void testNotifyEvent() throws Exception {
    
    
        final CEPStatement cepStatement = new CEPStatement();
        final ICEPEventStorage storage = Mockito.mock(ICEPEventStorage.class);
        
        cepStatement.addStorage(storage);
        cepStatement.notifyEvent("truc");
        Mockito.verify(storage, Mockito.atLeastOnce()).notifyEvent("truc");
    }
    
    
}
