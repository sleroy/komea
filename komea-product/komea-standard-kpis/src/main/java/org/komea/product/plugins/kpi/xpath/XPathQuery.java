/**
 * 
 */

package org.komea.product.plugins.kpi.xpath;



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
import org.komea.product.cep.query.FilterDefinition;
import org.komea.product.plugins.kpi.filters.EventTypeFilter;
import org.komea.product.plugins.kpi.filters.WithProjectFilter;



/**
 * This class defines the number of build per day.
 * "SELECT project as entity, COUNT(*) as value FROM Event.win:time(1 day) WHERE eventType.eventKey='build_started' GROUP BY project"
 * 
 * @author sleroy
 */
public class XPathQuery implements ICEPQueryImplementation
{
    
    
    /**
     * 
     */
    public XPathQuery() {
    
    
        super();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPQueryImplementation#getFilterDefinitions()
     */
    @Override
    public List<IFilterDefinition> getFilterDefinitions() {
    
    
        final IEventFilter<?> eventFilter =
                EventFilterBuilder.create().onlyIEvents().chain(new WithProjectFilter())
                        .chain(new EventTypeFilter("build_started")).build();
        final IFilterDefinition filterDefinition =
                FilterDefinition
                        .create()
                        .setCacheConfiguration(
                                CacheConfigurationBuilder.expirationTimeCache(1, TimeUnit.DAYS))
                        .setFilter(eventFilter).setFilterName("jenkins-filter");
        
        return Collections.singletonList(filterDefinition);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPQueryImplementation#getFormula()
     */
    @Override
    public ICEPFormula getFormula() {
    
    
        return new XPathFormula("count(/eventLists/events)");
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
