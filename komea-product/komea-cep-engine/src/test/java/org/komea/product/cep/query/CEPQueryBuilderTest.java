/**
 * 
 */

package org.komea.product.cep.query;



import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.komea.product.cep.api.ICEPQuery;
import org.komea.product.cep.api.IEventFilter;
import org.komea.product.cep.api.cache.ICacheConfiguration;
import org.komea.product.cep.cache.CacheConfigurationBuilder;
import org.komea.product.cep.filter.EventFilterBuilder;
import org.komea.product.cep.formula.CountFormula;
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
    
    
        final CEPQueryBuilder create = CEPQueryBuilder.create(new CountFormula());
        final IEventFilter eventFilter =
                EventFilterBuilder.create().onlyIEvents().chain(new BlockingEventFilter()).build();
        
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
}
