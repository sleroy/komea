/**
 *
 */

package org.komea.eventory.query;



import org.apache.commons.lang3.Validate;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.cache.ICacheConfiguration;
import org.komea.eventory.api.cache.ICacheStorageFactory;
import org.komea.eventory.api.engine.ICEPQuery;
import org.komea.eventory.api.filters.IEventFilter;
import org.komea.eventory.api.filters.IEventTransformer;
import org.komea.eventory.api.formula.ICEPFormula;
import org.komea.eventory.cache.CacheConfigurationBuilder;
import org.komea.eventory.filter.NoEventFilter;



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
    public static CEPQueryBuilder create(
            final ICEPFormula _formula,
            final ICacheStorageFactory _cacheStorageFactory) {


        return new CEPQueryBuilder(_formula, _cacheStorageFactory);

    }



    private final CEPQueryImplementation cepQueryDefinition = new CEPQueryImplementation();
    private final ICacheStorageFactory   factory;



    /**
     * CEP Query builder.
     *
     * @param _queryName
     *            the query name
     * @param _formula
     *            the formula
     */
    private CEPQueryBuilder(@SuppressWarnings("rawtypes")
    final ICEPFormula _formula, final ICacheStorageFactory _factory) {


        super();
        factory = _factory;
        Validate.notNull(_factory, "Cache storage factory must be provided.");
        cepQueryDefinition.setFormula(_formula);
    }


    public CEPQueryBuilder backupDelay(final BackupDelay _delay) {


        cepQueryDefinition.setBackupDelay(_delay);
        return this;
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


        cepQueryDefinition.addFilterDefinition(FilterDefinition.create()
                .setFilterName(_noEventFilter.toString())
                .setCacheConfiguration(CacheConfigurationBuilder.noConfiguration())
                .setFilter(_noEventFilter));
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


        cepQueryDefinition.addFilterDefinition(FilterDefinition.create()
                .setFilterName("stream" + numberOfFilters())
                .setCacheConfiguration(_cacheConfiguration).setFilter(_filter));
        return this;
    }


    /**
     * Defines a new filter for the CEP Query. This instance does not filter
     * events.
     *
     * @param _filter
     *            the filter name
     * @param _build
     *            the cache configuration.
     * @return the
     */
    @SuppressWarnings("unchecked")
    public CEPQueryBuilder defineFilter(final String _filter, final ICacheConfiguration _build) {


        cepQueryDefinition.addFilterDefinition(FilterDefinition.create().setFilterName(_filter)
                .setCacheConfiguration(_build).setFilter(new NoEventFilter()));
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


        cepQueryDefinition.addFilterDefinition(FilterDefinition.create().setFilterName(_filter)
                .setCacheConfiguration(_build).setFilter(_eventFilter));

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


        cepQueryDefinition.addFilterDefinition(FilterDefinition.create()
                .setFilterName("stream" + numberOfFilters())
                .setCacheConfiguration(_cacheConfiguration).setFilter(_filter)
                .setEventTransformer(_transformer));

        return this;
    }


    /**
     * Returns the CEP Query.
     */
    public CEPQueryImplementation getDefinition() {


        return cepQueryDefinition;

    }


    private int numberOfFilters() {


        return cepQueryDefinition.getFilterDefinitions().size();
    }
}
