package org.komea.product.plugins.kpi.standard;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.filters.IEventFilter;
import org.komea.eventory.api.filters.IFilterDefinition;
import org.komea.eventory.api.formula.ICEPFormula;
import org.komea.eventory.filter.EventFilterBuilder;
import org.komea.eventory.query.FilterDefinition;
import org.komea.product.cep.filter.OnlyEventFilter;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.plugins.kpi.filters.EventTypeFilter;
import org.komea.product.plugins.kpi.filters.WithProjectFilter;
import org.komea.product.plugins.kpi.formula.ProjectFormula;
import org.komea.product.plugins.kpi.standard.bugzilla.AbstractCEPQueryImplementation;

/**
 * "SELECT project as entity, last(value) as value FROM Event WHERE
 * eventType.eventKey='eventypeKey' GROUP BY project"
 */
public class LastEventValueKpi extends AbstractCEPQueryImplementation {

	private final String	eventTypeKey;

	public LastEventValueKpi(final String _eventTypeKey, final BackupDelay _backupDelay) {

		super(_backupDelay);
		eventTypeKey = _eventTypeKey;
		Validate.notNull(_backupDelay);
		Validate.notEmpty(_eventTypeKey);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.product.cep.api.ICEPQueryImplementation#getFilterDefinitions()
	 */
	@Override
	public List<IFilterDefinition> getFilterDefinitions() {

		final IEventFilter<?> eventFilter = EventFilterBuilder.create().chain(new OnlyEventFilter())
		        .chain(new WithProjectFilter()).chain(new EventTypeFilter(eventTypeKey)).build();
		final IFilterDefinition filterDefinition = FilterDefinition
		        .create()
		        .setCacheConfiguration(prepareCacheConfiguration().withCustomIndexer(new ProjectCacheIndexer()).build())
		        .setFilter(eventFilter).setFilterName(eventTypeKey + "-filter");

		return Collections.singletonList(filterDefinition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.product.cep.api.ICEPQueryImplementation#getFormula()
	 */
	@Override
	public ICEPFormula<IEvent, KpiResult> getFormula() {

		return new ProjectFormula(new EventValueFormula());
	}

}
