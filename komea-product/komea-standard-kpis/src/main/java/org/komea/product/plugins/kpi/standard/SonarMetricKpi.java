/**
 * 
 */

package org.komea.product.plugins.kpi.standard;



import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.komea.product.cep.api.ICEPFormula;
import org.komea.product.cep.api.ICEPQueryImplementation;
import org.komea.product.cep.api.IEventFilter;
import org.komea.product.cep.api.IFilterDefinition;
import org.komea.product.cep.cache.CacheConfigurationBuilder;
import org.komea.product.cep.filter.EventFilterBuilder;
import org.komea.product.cep.formula.tuple.EventCountFormula;
import org.komea.product.cep.formula.tuple.GroupByFormula;
import org.komea.product.cep.query.FilterDefinition;
import org.komea.product.plugins.kpi.tuplecreator.ProjectTupleCreator;



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
                EventFilterBuilder.create().onlyIEvents()
                        .chain(new SonarMetricEventFilter(metricName)).build();
        final ProjectCacheIndexer projectCacheIndexer = new ProjectCacheIndexer();
        final IFilterDefinition filterDefinition =
                FilterDefinition
                        .create()
                        .setCacheConfiguration(
                                CacheConfigurationBuilder.create().expirationTime(1, TimeUnit.DAYS)
                                        .withCustomIndexer(projectCacheIndexer).build())
                        .setFilter(eventFilter).setFilterName("jenkins-filter");
        
        return Collections.singletonList(filterDefinition);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPQueryImplementation#getFormula()
     */
    @Override
    public ICEPFormula getFormula() {
    
    
        return new GroupByFormula(new ProjectTupleCreator(), new EventCountFormula());
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPQueryImplementation#getParameters()
     */
    @Override
    public Map<String, Object> getParameters() {
    
    
        return Collections.EMPTY_MAP;
    }
    
}
