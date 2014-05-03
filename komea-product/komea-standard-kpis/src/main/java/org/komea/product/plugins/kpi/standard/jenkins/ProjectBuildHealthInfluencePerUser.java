/**
 * 
 */

package org.komea.product.plugins.kpi.standard.jenkins;



import java.util.Collections;
import java.util.HashSet;
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
import org.komea.product.plugins.kpi.filters.EventTypeFilter;
import org.komea.product.plugins.kpi.filters.WithUserFilter;
import org.komea.product.plugins.kpi.formula.UserFormula;
import org.komea.product.plugins.kpi.standard.jenkins.formula.ProjectBuildHealthFormula;
import org.komea.product.plugins.kpi.standard.jenkins.formula.ProjectBuildHealthFormula.HealthInformations;



/**
 * Number of broken builds per user.
 * 
 * @author sleroy
 */
public class ProjectBuildHealthInfluencePerUser implements ICEPQueryImplementation
{
    
    
    /**
     * 
     */
    public ProjectBuildHealthInfluencePerUser() {
    
    
        super();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPQueryImplementation#getFilterDefinitions()
     */
    @Override
    public List<IFilterDefinition> getFilterDefinitions() {
    
    
        final HashSet<String> eventSet = new HashSet<String>();
        eventSet.addAll(HealthInformations.positiveActionsSet);
        eventSet.addAll(HealthInformations.negativeActionsSet);
        final IEventFilter<?> eventFilter =
                EventFilterBuilder.create().chain(new OnlyEventFilter())
                        .chain(new WithUserFilter()).chain(new EventTypeFilter(eventSet)).build();
        final IFilterDefinition filterDefinition =
                FilterDefinition
                        .create()
                        .setCacheConfiguration(
                                CacheConfigurationBuilder.expirationTimeCache(7, TimeUnit.DAYS))
                        .setFilter(eventFilter).setFilterName("jenkins-brokenbuild-filter");
        
        return Collections.singletonList(filterDefinition);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPQueryImplementation#getFormula()
     */
    @Override
    public ICEPFormula<IEvent, KpiResult> getFormula() {
    
    
        return new UserFormula(new ProjectBuildHealthFormula());
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
