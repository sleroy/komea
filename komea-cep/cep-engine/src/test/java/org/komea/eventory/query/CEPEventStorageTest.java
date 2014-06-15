
package org.komea.eventory.query;



import java.io.Serializable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.eventory.api.filters.IEventFilter;
import org.komea.eventory.api.filters.IEventTransformer;
import org.komea.eventory.api.filters.ITransformedEvent;
import org.komea.eventory.cache.CacheConfigurationBuilder;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Strings;



@RunWith(MockitoJUnitRunner.class)
public class CEPEventStorageTest
{
    
    
    @Before
    public void before() {
    
    
    }
    
    
    @Test
    public final void testCEPEventStorage() throws Exception {
    
    
        final FilterDefinition filterDefinition =
                FilterDefinition.create()
                        .setCacheConfiguration(CacheConfigurationBuilder.noConfiguration())
                        .setFilter(Mockito.mock(IEventFilter.class))
                        .setEventTransformer(Mockito.mock(IEventTransformer.class));
        final CEPEventStorage<Serializable> cepEventStorage =
                new CEPEventStorage<Serializable>(filterDefinition);
        Assert.assertNotNull(cepEventStorage.getEventFilter());
        Assert.assertNotNull(cepEventStorage.getEventTransformer());
        Assert.assertNotNull(cepEventStorage.getCache());
        Assert.assertFalse(Strings.isNullOrEmpty(cepEventStorage.getFilterName()));
        
    }
    
    
    @Test
    public final void testNotifyEventWithFilteringAndTransformationWithoutValidTransformation()
            throws Exception {
    
    
        final IEventFilter mock = Mockito.mock(IEventFilter.class);
        final IEventTransformer mock2 = Mockito.mock(IEventTransformer.class);
        final CEPEventStorage<Serializable> cepEventStorage =
                new CEPEventStorage<Serializable>(FilterDefinition.create()
                        .setCacheConfiguration(CacheConfigurationBuilder.noConfiguration())
                        .setFilter(mock).setEventTransformer(mock2));
        
        final String event = "glouglougla";
        // Enable filtering
        Mockito.when(mock.isFiltered(event)).thenReturn(true);
        // Returns a transformed event
        final ITransformedEvent transformedEvent = Mockito.mock(ITransformedEvent.class);
        Mockito.when(mock2.transform(event)).thenReturn(transformedEvent);
        // Transformed event must be valid.
        Mockito.when(mock2.transform(event)).thenReturn(transformedEvent);
        Mockito.when(transformedEvent.isValid()).thenReturn(false); // Invalid transformation
        final String value = "gnignigni";
        Mockito.when(transformedEvent.getData()).thenReturn(value);
        cepEventStorage.notifyEvent(event);
        Mockito.verify(mock, Mockito.times(1)).isFiltered(event);
        Mockito.verify(mock2, Mockito.times(1)).transform(event);
        Mockito.verify(transformedEvent, Mockito.never()).getData();
        Mockito.verify(transformedEvent, Mockito.times(1)).isValid();
        
    }
    
    
    @Test
    public final void testNotifyEventWithFilteringAndTransformationWithValidTransformation()
            throws Exception {
    
    
        final IEventFilter mock = Mockito.mock(IEventFilter.class);
        final IEventTransformer mock2 = Mockito.mock(IEventTransformer.class);
        final CEPEventStorage<Serializable> cepEventStorage =
                new CEPEventStorage<Serializable>(FilterDefinition.create()
                        .setCacheConfiguration(CacheConfigurationBuilder.noConfiguration())
                        .setFilter(mock).setEventTransformer(mock2));
        
        final String event = "glouglougla";
        // Enable filtering
        Mockito.when(mock.isFiltered(event)).thenReturn(true);
        // Returns a transformed event
        final ITransformedEvent transformedEvent = Mockito.mock(ITransformedEvent.class);
        Mockito.when(mock2.transform(event)).thenReturn(transformedEvent);
        // Transformed event must be valid.
        Mockito.when(mock2.transform(event)).thenReturn(transformedEvent);
        Mockito.when(transformedEvent.isValid()).thenReturn(true); // Valid transformation
        final String value = "gnignigni";
        Mockito.when(transformedEvent.getData()).thenReturn(value);
        cepEventStorage.notifyEvent(event);
        Mockito.verify(mock, Mockito.times(1)).isFiltered(event);
        Mockito.verify(mock2, Mockito.times(1)).transform(event);
        Mockito.verify(transformedEvent, Mockito.times(1)).getData();
        Mockito.verify(transformedEvent, Mockito.times(1)).isValid();
        
    }
    
    
    @Test
    public final void testNotifyEventWithoutFiltering() throws Exception {
    
    
        final IEventFilter mock = Mockito.mock(IEventFilter.class);
        final IEventTransformer mock2 = Mockito.mock(IEventTransformer.class);
        final CEPEventStorage<Serializable> cepEventStorage =
                new CEPEventStorage<Serializable>(FilterDefinition.create()
                        .setCacheConfiguration(CacheConfigurationBuilder.noConfiguration())
                        .setFilter(mock).setEventTransformer(mock2));
        
        final String event = "glouglougla";
        cepEventStorage.notifyEvent(event);
        Mockito.verify(mock, Mockito.times(1)).isFiltered(event);
        Mockito.verify(mock2, Mockito.never()).transform(event);
        
    }
}
