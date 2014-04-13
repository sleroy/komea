/**
 * 
 */

package org.komea.product.cep.query;



import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.junit.Test;
import org.komea.eventory.cache.CacheConfigurationBuilder;
import org.komea.eventory.filter.EventFilterBuilder;
import org.komea.eventory.formula.CountFormula;
import org.komea.eventory.query.CEPQueryBuilder;
import org.komea.eventory.utils.PluginUtils;
import org.komea.product.cep.api.ICEPQuery;
import org.komea.product.cep.api.IEventFilter;
import org.komea.product.cep.api.cache.ICacheConfiguration;
import org.komea.product.cep.filter.OnlyEventFilter;
import org.komea.product.database.alert.EventBuilder;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;



/**
 * @author sleroy
 */
public class CEPQueryBuilderTest
{
    
    
    @Test
    public void testBuildingQuery() {
        PluginUtils.setCacheStorageFactory(new CacheStorageFa);
    
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
    
    
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.cep.query.CEPQueryBuilder#defineFilter(org.komea.eventory.api.IEventFilter, org.komea.eventory.api.cache.ICacheConfiguration)}
     * .
     */
    @Test
    @Ignore
    public void testDefineFilterIEventFilterICacheConfiguration() throws Exception {
    
    
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.cep.query.CEPQueryBuilder#defineIEventFilter(org.komea.eventory.api.cache.ICacheConfiguration)}.
     */
    @Test
    @Ignore
    public void testDefineIEventFilter() throws Exception {
    
    
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.query.CEPQueryBuilder#numberOfFilters()}.
     */
    @Test
    @Ignore
    public void testNumberOfFilters() throws Exception {
    
    
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.query.CEPQueryBuilder#withParams(java.util.Map)}.
     */
    @Test
    @Ignore
    public void testWithParams() throws Exception {
    
    
        throw new RuntimeException("not yet implemented");
    }
}
