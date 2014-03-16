/**
 * 
 */

package org.komea.product.cep.query;



import java.util.concurrent.TimeUnit;

import org.komea.product.cep.api.IEventFilter;
import org.komea.product.cep.api.IEventTransformer;
import org.komea.product.cep.api.IFilterDefinition;
import org.komea.product.cep.api.cache.ICacheConfiguration;
import org.komea.product.cep.cache.CacheConfigurationBuilder;
import org.komea.product.cep.filter.NoEventFilter;



/**
 * @author sleroy
 */
public class FilterDefinition implements IFilterDefinition
{
    
    
    /**
     * Builds a default filter definition.
     */
    public static FilterDefinition create() {
    
    
        final FilterDefinition filterDefinition = new FilterDefinition();
        filterDefinition.cacheConfiguration =
                CacheConfigurationBuilder.expirationTimeCache(1, TimeUnit.HOURS);
        filterDefinition.filter = new NoEventFilter();
        filterDefinition.filterName = "none";
        filterDefinition.eventTransformer = null;
        return filterDefinition;
        
    }
    
    
    
    private ICacheConfiguration     cacheConfiguration;
    
    
    private IEventTransformer<?, ?> eventTransformer;
    
    
    private IEventFilter            filter;
    
    
    private String                  filterName;
    
    
    
    private FilterDefinition() {
    
    
        super();
    }
    
    
    /**
     * @return the cacheConfiguration
     */
    @Override
    public ICacheConfiguration getCacheConfiguration() {
    
    
        return cacheConfiguration;
    }
    
    
    /**
     * @return the eventTransformer
     */
    @Override
    public IEventTransformer<?, ?> getEventTransformer() {
    
    
        return eventTransformer;
    }
    
    
    /**
     * @return the filter
     */
    @Override
    public IEventFilter getFilter() {
    
    
        return filter;
    }
    
    
    /**
     * @return the filterName
     */
    @Override
    public String getFilterName() {
    
    
        return filterName;
    }
    
    
    /**
     * @param _cacheConfiguration
     *            the cacheConfiguration to set
     * @return
     */
    public FilterDefinition setCacheConfiguration(final ICacheConfiguration _cacheConfiguration) {
    
    
        cacheConfiguration = _cacheConfiguration;
        return this;
    }
    
    
    /**
     * @param _eventTransformer
     *            the eventTransformer to set
     * @return
     */
    public FilterDefinition setEventTransformer(final IEventTransformer<?, ?> _eventTransformer) {
    
    
        eventTransformer = _eventTransformer;
        return this;
    }
    
    
    /**
     * @param _filter
     *            the filter to set
     * @return
     */
    public FilterDefinition setFilter(final IEventFilter _filter) {
    
    
        filter = _filter;
        return this;
    }
    
    
    /**
     * @param _filterName
     *            the filterName to set
     * @return
     */
    public FilterDefinition setFilterName(final String _filterName) {
    
    
        filterName = _filterName;
        return this;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "FilterDefinition [cacheConfiguration="
                + cacheConfiguration + ", filter=" + filter + ", filterName=" + filterName
                + ", eventTransformer=" + eventTransformer + "]";
    }
    
}
