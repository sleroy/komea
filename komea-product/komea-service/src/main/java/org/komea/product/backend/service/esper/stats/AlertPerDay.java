/**
 * 
 */

package org.komea.product.backend.service.esper.stats;



import java.util.concurrent.TimeUnit;

import org.komea.eventory.api.cache.ICacheConfiguration;
import org.komea.eventory.api.filters.IEventFilter;
import org.komea.eventory.cache.CacheConfigurationBuilder;
import org.komea.eventory.filter.EventFilterBuilder;
import org.komea.eventory.formula.CountFormula;
import org.komea.eventory.query.CEPQueryImplementation;
import org.komea.eventory.query.FilterDefinition;
import org.komea.product.cep.filter.OnlyEventFilter;



/**
 * Builds a KPI that returns a numerical value (number of alerts per day )
 * 
 * @author sleroy
 */
public class AlertPerDay extends CEPQueryImplementation
{
    
    
    public AlertPerDay() {
    
    
        super();
        setFormula(new CountFormula());
        final ICacheConfiguration expirationTimeCache =
                CacheConfigurationBuilder.expirationTimeCache(24, TimeUnit.HOURS);
        final IEventFilter<?> eventFilter =
                EventFilterBuilder.create().chain(new OnlyEventFilter()).build();
        addFilterDefinition(FilterDefinition.create().setCacheConfiguration(expirationTimeCache)
                .setFilter(eventFilter));
    }
}
