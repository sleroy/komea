/**
 * 
 */

package org.komea.product.plugins.scm.kpi;



import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.eventory.api.filters.IFilterDefinition;
import org.komea.eventory.api.formula.ICEPFormula;
import org.komea.eventory.cache.CacheConfigurationBuilder;
import org.komea.eventory.filter.EventFilterBuilder;
import org.komea.product.plugins.scm.api.plugin.ICommitFunction;



/**
 * @author sleroy
 */
public abstract class ScmQueryImplementation implements ICEPQueryImplementation
{
    
    
    /**
     * Returns the commit function.
     * 
     * @return the commit function.
     */
    public abstract ICommitFunction getCommitFunction();
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.engine.ICEPQueryImplementation#getFilterDefinitions()
     */
    @Override
    public List<IFilterDefinition> getFilterDefinitions() {
    
    
        return Collections.singletonList(EventFilterBuilder
                .create()
                .chain(new ScmCommitFilter())
                .buildFilterDefinition("scmcommit-filter",
                        CacheConfigurationBuilder.expirationTimeCache(1, TimeUnit.DAYS)));
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.engine.ICEPQueryImplementation#getFormula()
     */
    @Override
    public ICEPFormula getFormula() {
    
    
        return new UserCommitFormula(getCommitFunction());
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.engine.ICEPQueryImplementation#getParameters()
     */
    @Override
    public Map<String, Object> getParameters() {
    
    
        return Collections.EMPTY_MAP;
    }
    
}
