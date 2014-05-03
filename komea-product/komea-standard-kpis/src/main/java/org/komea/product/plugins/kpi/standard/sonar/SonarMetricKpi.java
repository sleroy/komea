/**
 * 
 */

package org.komea.product.plugins.kpi.standard.sonar;



import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.eventory.api.filters.IEventFilter;
import org.komea.eventory.api.filters.IFilterDefinition;
import org.komea.eventory.api.formula.ICEPFormula;
import org.komea.eventory.cache.CacheConfigurationBuilder;
import org.komea.eventory.filter.EventFilterBuilder;
import org.komea.eventory.query.FilterDefinition;
import org.komea.product.cep.filter.OnlyEventFilter;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.plugins.kpi.filters.SonarMetricEventFilter;
import org.komea.product.plugins.kpi.filters.WithProjectFilter;
import org.komea.product.plugins.kpi.formula.ProjectFormula;
import org.komea.product.plugins.kpi.standard.EventValueFormula;
import org.komea.product.plugins.kpi.standard.ProjectCacheIndexer;



/**
 * "SELECT project as entity, last(value) as value FROM Event WHERE eventType.eventKey='metric_value' "
 * + "AND properties('metricName') = '"
 * + _metricName
 * + "' "
 * + "GROUP BY project"
 * 
 * @author sleroy
 */
public class SonarMetricKpi implements ICEPQueryImplementation
{
    
    
    private final String metricName;
    
    
    
    /**
     * 
     */
    public SonarMetricKpi(final String _metricName) {
    
    
        super();
        metricName = _metricName;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPQueryImplementation#getFilterDefinitions()
     */
    @Override
    public List<IFilterDefinition> getFilterDefinitions() {
    
    
        final IEventFilter<?> eventFilter =
                EventFilterBuilder.create().chain(new OnlyEventFilter())
                        .chain(new WithProjectFilter())
                        .chain(new SonarMetricEventFilter(metricName)).build();
        final IFilterDefinition filterDefinition =
                FilterDefinition
                        .create()
                        .setCacheConfiguration(
                                CacheConfigurationBuilder.create().expirationTime(1, TimeUnit.DAYS)
                                        .withCustomIndexer(new ProjectCacheIndexer()).build())
                        .setFilter(eventFilter).setFilterName("jenkins-filter");
        
        return Collections.singletonList(filterDefinition);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPQueryImplementation#getFormula()
     */
    @Override
    public ICEPFormula<IEvent, KpiResult> getFormula() {
    
    
        return new ProjectFormula(new EventValueFormula());
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPQueryImplementation#getParameters()
     */
    @Override
    public Map<String, Object> getParameters() {
    
    
        return Collections.emptyMap();
    }
    
}
