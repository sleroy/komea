/**
 * 
 */

package org.komea.product.plugins.kpi.standard.jenkins;

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
 * This class defines the number of build per day.
 * "SELECT project as entity, COUNT(*) as value FROM Event.win:time(1 day) WHERE eventType.eventKey='build_started' GROUP BY project"
 * 
 * @author sleroy
 */
public class BuildPerDay extends AbstractCEPQueryImplementation {

	/**
     * 
     */
	public BuildPerDay() {

		super(BackupDelay.DAY);
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
		        .chain(new WithProjectFilter()).chain(new EventTypeFilter("build_started")).build();
		final IFilterDefinition filterDefinition = FilterDefinition.create()
		        .setCacheConfiguration(buildExpirationCache()).setFilter(eventFilter).setFilterName("jenkins-filter");

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
