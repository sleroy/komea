/**
 * 
 */
package org.komea.product.backend.service.esper.stats;

import java.util.concurrent.TimeUnit;

import org.komea.product.backend.service.esper.EventSeverityFilter;
import org.komea.product.cep.api.IEventFilter;
import org.komea.product.cep.api.cache.ICacheConfiguration;
import org.komea.product.cep.cache.CacheConfigurationBuilder;
import org.komea.product.cep.filter.EventFilterBuilder;
import org.komea.product.cep.formula.CountFormula;
import org.komea.product.cep.query.FilterDefinition;
import org.komea.product.cep.query.QueryDefinition;
import org.komea.product.database.enums.Severity;

/**
 * Builds a KPI that returns a numerical value (number of alerts per day per severity)
 * 
 * @author sleroy
 */
public class AlertPerSeverityPerDay extends QueryDefinition
{
    
    
    public AlertPerSeverityPerDay(final Severity _severity) {
    
    
        // "SELECT COUNT(*) as alert_number FROM Event(eventType.severity=Severity."
        // + _criticity.name() + ")" + ".win:time(24 hour)"
        super("Alert per severity per day");
        setFormula(new CountFormula());
        final ICacheConfiguration expirationTimeCache =
                CacheConfigurationBuilder.expirationTimeCache(24, TimeUnit.HOURS);
        final IEventFilter<?> eventFilter =
                EventFilterBuilder.create().onlyIEvents()
                        .chain(new EventSeverityFilter(_severity)).build();
        addFilterDefinition(new FilterDefinition(expirationTimeCache, eventFilter, null));
        
    }
}