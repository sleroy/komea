/**
 * 
 */

package org.komea.product.backend.service.esper.stats;



import java.util.concurrent.TimeUnit;

import org.komea.product.cep.api.IEventFilter;
import org.komea.product.cep.api.cache.ICacheConfiguration;
import org.komea.product.cep.cache.CacheConfigurationBuilder;
import org.komea.product.cep.filter.EventFilterBuilder;
import org.komea.product.cep.formula.CountFormula;
import org.komea.product.cep.query.CEPQueryImplementation;
import org.komea.product.cep.query.FilterDefinition;



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
        final IEventFilter<?> eventFilter = EventFilterBuilder.create().onlyIEvents().build();
        addFilterDefinition(new FilterDefinition(expirationTimeCache, eventFilter, null));
        
    }
}
