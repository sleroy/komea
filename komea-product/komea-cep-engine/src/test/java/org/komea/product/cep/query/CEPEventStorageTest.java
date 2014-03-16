
package org.komea.product.cep.query;



import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.cep.api.IEventFilter;
import org.komea.product.cep.api.IEventTransformer;
import org.komea.product.cep.api.ITransformedEvent;
import org.komea.product.cep.cache.CacheConfigurationBuilder;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Strings;



@RunWith(MockitoJUnitRunner.class)
public class CEPEventStorageTest
{
    
    
    @Test
    public final void testCEPEventStorage() throws Exception {
    
    
        final CEPEventStorage<Serializable> cepEventStorage =
                new CEPEventStorage<Serializable>(new FilterDefinition(
                        CacheConfigurationBuilder.noConfiguration(),
                        Mockito.mock(IEventFilter.class), Mockito.mock(IEventTransformer.class)));
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
                new CEPEventStorage<Serializable>(new FilterDefinition(
                        CacheConfigurationBuilder.noConfiguration(), mock, mock2));
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
                new CEPEventStorage<Serializable>(new FilterDefinition(
                        CacheConfigurationBuilder.noConfiguration(), mock, mock2));
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
                new CEPEventStorage<Serializable>(new FilterDefinition(
                        CacheConfigurationBuilder.noConfiguration(), mock, mock2));
        final String event = "glouglougla";
        cepEventStorage.notifyEvent(event);
        Mockito.verify(mock, Mockito.times(1)).isFiltered(event);
        Mockito.verify(mock2, Mockito.never()).transform(event);
        
    }
}
