/**
 * 
 */

package org.komea.product.plugins.kpi.standard;



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
import org.komea.eventory.formula.tuple.EventCountFormula;
import org.komea.eventory.formula.tuple.GroupByFormula;
import org.komea.eventory.query.FilterDefinition;
import org.komea.product.cep.filter.OnlyEventFilter;
import org.komea.product.plugins.kpi.filters.EventTypeFilter;
import org.komea.product.plugins.kpi.filters.WithProjectFilter;
import org.komea.product.plugins.kpi.tuplecreator.ProjectTupleCreator;



/**
 * This class defines the number of build per day.
 * "SELECT project as entity, COUNT(*) as value FROM Event.win:time(1 day) WHERE eventType.eventKey='build_started' GROUP BY project"
 * 
 * @author sleroy
 */
public class SuccessfulBuildPerMonth implements ICEPQueryImplementation
{
    
    
    /**
     * 
     */
    public SuccessfulBuildPerMonth() {
    
    
        super();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPQueryImplementation#getFilterDefinitions()
     */
    @Override
    public List<IFilterDefinition> getFilterDefinitions() {
    
    
        final IEventFilter<?> eventFilter =
                EventFilterBuilder.create().chain(new OnlyEventFilter()).chain(new WithProjectFilter())
                        .chain(new EventTypeFilter("build_complete")).build();
        final IFilterDefinition filterDefinition =
                FilterDefinition
                        .create()
                        .setCacheConfiguration(
                                CacheConfigurationBuilder.expirationTimeCache(31, TimeUnit.DAYS))
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
