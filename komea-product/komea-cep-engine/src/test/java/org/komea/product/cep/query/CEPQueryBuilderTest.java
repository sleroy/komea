/**
 * 
 */

package org.komea.product.cep.query;



import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.junit.Test;
import org.komea.eventory.api.cache.ICacheConfiguration;
import org.komea.eventory.api.cache.ICacheStorage;
import org.komea.eventory.api.cache.ICacheStorageFactory;
import org.komea.eventory.api.engine.ICEPQuery;
import org.komea.eventory.api.filters.IEventFilter;
import org.komea.eventory.cache.CacheConfigurationBuilder;
import org.komea.eventory.cache.guava.GoogleCacheStorage;
import org.komea.eventory.filter.EventFilterBuilder;
import org.komea.eventory.formula.CountFormula;
import org.komea.eventory.query.CEPQueryBuilder;
import org.komea.eventory.utils.PluginUtils;
import org.komea.product.cep.filter.BlockingEventFilter;
import org.komea.product.cep.filter.OnlyEventFilter;
import org.komea.product.database.alert.EventBuilder;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



/**
 * @author sleroy
 */
public class CEPQueryBuilderTest
{
    
    
    @Test
    public void testBuildingQuery() {
    
    
        final ICacheStorageFactory mock = mock(ICacheStorageFactory.class);
        final Answer<ICacheStorage> answer = new Answer<ICacheStorage>()
        {
            
            
            @Override
            public ICacheStorage answer(final InvocationOnMock _invocation) throws Throwable {
            
            
                return new GoogleCacheStorage((ICacheConfiguration) _invocation.getArguments()[0]);
            }
        };
        when(mock.newCacheStorage(Matchers.any(ICacheConfiguration.class))).thenAnswer(answer);
        PluginUtils.setCacheStorageFactory(mock);
        final CEPQueryBuilder create = CEPQueryBuilder.create(new CountFormula());
        final IEventFilter eventFilter =
                EventFilterBuilder.create().chain(new OnlyEventFilter())
                        .chain(new BlockingEventFilter()).build();
        
        final ICacheConfiguration hours24CacheConfig =
                CacheConfigurationBuilder.create().expirationTime(24, TimeUnit.HOURS).build();
        final ICacheConfiguration max5ItemsCacheConfiguration =
                CacheConfigurationBuilder.create().maximumSize(5).build();
        
        final ICEPQuery query =
                create.defineFilter("last-24h-alerts", hours24CacheConfig)
                        .defineFilter("last-5-blocking-alerts", eventFilter,
                                max5ItemsCacheConfiguration).build();
        //
        final EventType eventType = new EventType();
        eventType.setSeverity(Severity.BLOCKER);
        query.notifyEvent(EventBuilder.newAlert().message("Example of event").eventType(eventType)
                .build());
        
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.cep.query.CEPQueryBuilder#defineFilterAndTransformer(org.komea.eventory.api.IEventFilter, org.komea.eventory.api.IEventTransformer, org.komea.eventory.api.cache.ICacheConfiguration)}
     * .
     */
    @Test
    @Ignore
    public void testDefineFilterAndTransformer() throws Exception {
    
    
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.cep.query.CEPQueryBuilder#defineFilter(org.komea.eventory.api.IEventFilter, org.komea.eventory.api.cache.ICacheConfiguration)}
     * .
     */
    @Test
    @Ignore
    public void testDefineFilterIEventFilterICacheConfiguration() throws Exception {
    
    
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.cep.query.CEPQueryBuilder#defineIEventFilter(org.komea.eventory.api.cache.ICacheConfiguration)}.
     */
    @Test
    @Ignore
    public void testDefineIEventFilter() throws Exception {
    
    
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.query.CEPQueryBuilder#numberOfFilters()}.
     */
    @Test
    @Ignore
    public void testNumberOfFilters() throws Exception {
    
    
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.query.CEPQueryBuilder#withParams(java.util.Map)}.
     */
    @Test
    @Ignore
    public void testWithParams() throws Exception {
    
    
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
}
