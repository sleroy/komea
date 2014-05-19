package org.komea.product.plugins.kpi.standard;

import java.util.Collections;
import java.util.List;

import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.filters.IEventFilter;
import org.komea.eventory.api.filters.IFilterDefinition;
import org.komea.eventory.api.formula.ICEPFormula;
import org.komea.eventory.filter.EventFilterBuilder;
import org.komea.eventory.query.FilterDefinition;
import org.komea.product.cep.filter.OnlyEventFilter;
import org.komea.product.cep.formula.EventCountFormula;
import org.komea.product.plugins.kpi.filters.EventTypeFilter;
import org.komea.product.plugins.kpi.filters.WithProjectFilter;
import org.komea.product.plugins.kpi.formula.ProjectFormula;
import org.komea.product.plugins.kpi.standard.bugzilla.AbstractCEPQueryImplementation;

/**
 * "SELECT project as entity, * COUNT(*) as value FROM Event.win:time('time'
 * 'unit') WHERE eventType.eventKey='eventTypeKey' GROUP BY project"
 * 
 * @author sleroy
 */
public class EventsCountKpi extends AbstractCEPQueryImplementation {

	private final String	eventTypeKey;

	public EventsCountKpi(final String eventTypeKey, final BackupDelay _backupDelay) {

		super(_backupDelay);
		this.eventTypeKey = eventTypeKey;

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
		final IFilterDefinition filterDefinition = FilterDefinition.create()
		        .setCacheConfiguration(buildExpirationCache()).setFilter(eventFilter)
		        .setFilterName(eventTypeKey + "-" + time + timeUnit + "-filter");

		return Collections.singletonList(filterDefinition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.product.cep.api.ICEPQueryImplementation#getFormula()
	 */
	@Override
	public ICEPFormula getFormula() {

		return new ProjectFormula(new EventCountFormula());
	}
}
