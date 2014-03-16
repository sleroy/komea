/**
 * 
 */

package org.komea.product.cep.query;



import java.io.Serializable;
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
    
    
    private ICacheConfiguration     cacheConfiguration;
    
    
    private IEventTransformer<?, ?> eventTransformer;
    
    
    private IEventFilter            filter;
    
    
    private String                  filterName;
    
    
    
    /**
     * Builds a default filter definition.
     */
    public FilterDefinition() {
    
    
        super();
        cacheConfiguration = CacheConfigurationBuilder.expirationTimeCache(1, TimeUnit.HOURS);
        filter = new NoEventFilter();
        filterName = "none";
        eventTransformer = null;
    }
    
    
    /**
     * @param _expirationTimeCache
     */
    public FilterDefinition(final ICacheConfiguration _expirationTimeCache) {
    
    
        this("filter", _expirationTimeCache, new NoEventFilter(), null);
    }
    
    
    /**
     * @param _expirationTimeCache
     * @param _eventFilter
     * @param _object
     */
    public FilterDefinition(
            final ICacheConfiguration _expirationTimeCache,
            final IEventFilter<?> _eventFilter,
            final IEventTransformer<Serializable, Serializable> _object) {
    
    
        this("filter", _expirationTimeCache, _eventFilter, _object);
    }
    
    
    /**
     * @param _string
     * @param _cacheConfiguration
     * @param _filter
     * @param _transformer
     */
    public FilterDefinition(
            final String _filterName,
            final ICacheConfiguration _cacheConfiguration,
            @SuppressWarnings("rawtypes")
            final IEventFilter _filter,
            final IEventTransformer<?, ?> _transformer) {
    
    
        filterName = _filterName;
        cacheConfiguration = _cacheConfiguration;
        filter = _filter;
        eventTransformer = _transformer;
        
        
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
     */
    public void setCacheConfiguration(final ICacheConfiguration _cacheConfiguration) {
    
    
        cacheConfiguration = _cacheConfiguration;
    }
    
    
    /**
     * @param _eventTransformer
     *            the eventTransformer to set
     */
    public void setEventTransformer(final IEventTransformer<?, ?> _eventTransformer) {
    
    
        eventTransformer = _eventTransformer;
    }
    
    
    /**
     * @param _filter
     *            the filter to set
     */
    public void setFilter(final IEventFilter _filter) {
    
    
        filter = _filter;
    }
    
    
    /**
     * @param _filterName
     *            the filterName to set
     */
    public void setFilterName(final String _filterName) {
    
    
        filterName = _filterName;
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
