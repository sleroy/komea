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
import org.komea.product.backend.service.esper.EventSeverityFilter;
import org.komea.product.cep.filter.OnlyEventFilter;
import org.komea.product.database.enums.Severity;



/**
 * Builds a KPI that returns a numerical value (number of alerts per day per severity)
 * 
 * @author sleroy
 */
public class AlertPerSeverityPerDay extends CEPQueryImplementation
{
    
    
    public AlertPerSeverityPerDay(final Severity _severity) {
    
    
        // "SELECT COUNT(*) as alert_number FROM Event(eventType.severity=Severity."
        // + _criticity.name() + ")" + ".win:time(24 hour)"
        super();
        setFormula(new CountFormula());
        final ICacheConfiguration expirationTimeCache =
                CacheConfigurationBuilder.expirationTimeCache(24, TimeUnit.HOURS);
        final IEventFilter<?> eventFilter =
                EventFilterBuilder.create().chain(new OnlyEventFilter())
                        .chain(new EventSeverityFilter(_severity)).build();
        addFilterDefinition(FilterDefinition.create().setCacheConfiguration(expirationTimeCache)
                .setFilter(eventFilter));
        
    }
}
