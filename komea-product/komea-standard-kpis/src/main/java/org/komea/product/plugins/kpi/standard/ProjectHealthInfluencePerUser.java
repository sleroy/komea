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
import org.komea.product.cep.formula.tuple.GroupByFormula;
import org.komea.product.cep.query.FilterDefinition;
import org.komea.product.plugins.kpi.filters.EventTypeFilter;
import org.komea.product.plugins.kpi.filters.WithUserFilter;
import org.komea.product.plugins.kpi.tuplecreator.UserTupleCreator;



/**
 * Number of broken builds per user.
 * 
 * @author sleroy
 */
public class ProjectHealthInfluencePerUser implements ICEPQueryImplementation
{
    
    
    /**
     * 
     */
    public ProjectHealthInfluencePerUser() {
    
    
        super();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPQueryImplementation#getFilterDefinitions()
     */
    @Override
    public List<IFilterDefinition> getFilterDefinitions() {
    
    
        final IEventFilter<?> eventFilter =
                EventFilterBuilder.create().onlyIEvents().chain(new WithUserFilter())
                        .chain(new EventTypeFilter("build_broken", "build_fixed")).build();
        final IFilterDefinition filterDefinition =
                FilterDefinition
                        .create()
                        .setCacheConfiguration(
                                CacheConfigurationBuilder.expirationTimeCache(3, TimeUnit.DAYS))
                        .setFilter(eventFilter).setFilterName("jenkins-brokenbuild-filter");
        
        return Collections.singletonList(filterDefinition);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPQueryImplementation#getFormula()
     */
    @Override
    public ICEPFormula<?> getFormula() {
    
    
        return new GroupByFormula(new UserTupleCreator(), new HealthFormula());
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
