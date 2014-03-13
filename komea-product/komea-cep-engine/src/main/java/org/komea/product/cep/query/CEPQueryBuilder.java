/**
 * 
 */

package org.komea.product.cep.query;



import java.util.Map;

import org.komea.product.cep.api.ICEPFormula;
import org.komea.product.cep.api.ICEPQuery;
import org.komea.product.cep.api.IEventFilter;
import org.komea.product.cep.api.IEventTransformer;
import org.komea.product.cep.api.cache.ICacheConfiguration;
import org.komea.product.cep.cache.CacheConfigurationBuilder;
import org.komea.product.cep.filter.EventFilterBuilder;
import org.komea.product.cep.filter.NoEventFilter;



/**
 * This class defines a CEP Query builder;
 * 
 * @author sleroy
 */
public class CEPQueryBuilder
{
    
    
    /**
     * Builds a new CEP Query
     * 
     * @param _queryName
     *            the query name;
     */
    public static CEPQueryBuilder create(final ICEPFormula _formula) {
    
    
        final CEPQueryBuilder cepQueryBuilder = new CEPQueryBuilder(_formula);
        cepQueryBuilder.defineFilter(new NoEventFilter());
        return cepQueryBuilder;
        
    }
    
    
    
    private final CEPQueryImplementation cepQueryDefinition = new CEPQueryImplementation();
    
    
    
    /**
     * CEP Query builder.
     * 
     * @param _queryName
     *            the query name
     * @param _formula
     *            the formula
     */
    private CEPQueryBuilder(final ICEPFormula _formula) {
    
    
        super();
        
        cepQueryDefinition.setFormula(_formula);
    }
    
    
    /**
     * Returns the CEP Query.
     */
    public ICEPQuery build() {
    
    
        return new CEPQuery(cepQueryDefinition);
        
    }
    
    
    /**
     * Defines a filter without cache configuration strategy.
     * 
     * @param _noEventFilter
     *            the filter
     * @return the query definition.
     */
    public CEPQueryBuilder defineFilter(final IEventFilter _noEventFilter) {
    
    
        cepQueryDefinition.addFilterDefinition(new FilterDefinition(_noEventFilter.toString(),
                CacheConfigurationBuilder.noConfiguration(), _noEventFilter, null));
        return this;
    }
    
    
    /**
     * Attach a ievent filter with the given cache configuration.
     * 
     * @param _cacheConfiguration
     * @return
     */
    @SuppressWarnings("unchecked")
    public CEPQueryBuilder defineFilter(
            final IEventFilter _filter,
            final ICacheConfiguration _cacheConfiguration) {
    
    
        cepQueryDefinition.addFilterDefinition(new FilterDefinition("stream" + numberOfFilters(),
                _cacheConfiguration, _filter, null));
        return this;
    }
    
    
    /**
     * Defines a new filter for the CEP Query. This instance does not filter events.
     * 
     * @param _filter
     *            the filter name
     * @param _build
     *            the cache configuration.
     * @return the
     */
    @SuppressWarnings("unchecked")
    public CEPQueryBuilder defineFilter(final String _filter, final ICacheConfiguration _build) {
    
    
        cepQueryDefinition.addFilterDefinition(new FilterDefinition(_filter, _build,
                new NoEventFilter(), null));
        return this;
    }
    
    
    /**
     * Defines a new filter for the CEP Query.
     * 
     * @param _filter
     *            the filter name
     * @param the
     *            event filter to provider
     * @param _build
     *            the cache configuration.
     * @return the
     */
    @SuppressWarnings({
            "unchecked", "rawtypes" })
    public CEPQueryBuilder defineFilter(
            final String _filter,
            final IEventFilter<?> _eventFilter,
            final ICacheConfiguration _build) {
    
    
        cepQueryDefinition.addFilterDefinition(new FilterDefinition(_filter, _build, _eventFilter,
                null));
        return this;
    }
    
    
    /**
     * Attach a filter and a transformer.
     */
    @SuppressWarnings("unchecked")
    public CEPQueryBuilder defineFilterAndTransformer(
            final IEventFilter _filter,
            final IEventTransformer _transformer,
            final ICacheConfiguration _cacheConfiguration) {
    
    
        cepQueryDefinition.addFilterDefinition(new FilterDefinition("stream" + numberOfFilters(),
                _cacheConfiguration, _filter, _transformer));
        return this;
    }
    
    
    /**
     * Attach a ievent filter with the given cache configuration.
     * 
     * @param _cacheConfiguration
     * @return
     */
    @SuppressWarnings("unchecked")
    public CEPQueryBuilder defineIEventFilter(final ICacheConfiguration _cacheConfiguration) {
    
    
        cepQueryDefinition.addFilterDefinition(new FilterDefinition("stream" + numberOfFilters(),
                _cacheConfiguration, EventFilterBuilder.create().onlyIEvents().build(), null));
        return this;
    }
    
    
    /**
     * Returns the CEP Query.
     */
    public CEPQueryImplementation getDefinition() {
    
    
        return cepQueryDefinition;
        
    }
    
    
    /**
     * With parametes
     * 
     * @param _parameters
     *            the parametes
     * @return the parameters
     */
    public CEPQueryBuilder withParams(final Map<String, Object> _parameters) {
    
    
        cepQueryDefinition.setParameters(_parameters);
        return this;
    }
    
    
    private int numberOfFilters() {
    
    
        return cepQueryDefinition.getFilterDefinitions().size();
    }
}
