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
import org.komea.eventory.formula.tuple.GroupByFormula;
import org.komea.eventory.query.FilterDefinition;
import org.komea.product.cep.filter.OnlyEventFilter;
import org.komea.product.plugins.kpi.filters.WithProjectFilter;
import org.komea.product.plugins.kpi.tuplecreator.ProjectTupleCreator;

/**
 * "SELECT project as entity, last(value) as value FROM Event WHERE
 * eventType.eventKey='eventypeKey' GROUP BY project"
 *
 */
public class LastEventValueKpi implements ICEPQueryImplementation {

    private final String eventTypeKey;

    public LastEventValueKpi(final String _eventTypeKey) {

        super();
        eventTypeKey = _eventTypeKey;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPQueryImplementation#getFilterDefinitions()
     */
    @Override
    public List<IFilterDefinition> getFilterDefinitions() {

        final IEventFilter<?> eventFilter = EventFilterBuilder.create()
                .chain(new OnlyEventFilter()).chain(new WithProjectFilter()).build();
        final IFilterDefinition filterDefinition
                = FilterDefinition
                .create()
                .setCacheConfiguration(
                        CacheConfigurationBuilder.create().expirationTime(1, TimeUnit.DAYS)
                        .withCustomIndexer(new ProjectCacheIndexer()).build())
                .setFilter(eventFilter).setFilterName(eventTypeKey + "-filter");

        return Collections.singletonList(filterDefinition);
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPQueryImplementation#getFormula()
     */
    @Override
    public ICEPFormula<?> getFormula() {

        return new GroupByFormula(new ProjectTupleCreator(), new EventValueFormula());
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPQueryImplementation#getParameters()
     */
    @Override
    public Map<String, Object> getParameters() {

        return Collections.emptyMap();
    }

}
