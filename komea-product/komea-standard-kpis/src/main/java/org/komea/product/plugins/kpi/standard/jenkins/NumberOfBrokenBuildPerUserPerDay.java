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
import org.komea.product.database.dto.KpiResult;
import org.komea.product.plugins.kpi.filters.EventTypeFilter;
import org.komea.product.plugins.kpi.filters.WithUserFilter;
import org.komea.product.plugins.kpi.formula.UserFormula;
import org.komea.product.plugins.kpi.standard.bugzilla.AbstractCEPQueryImplementation;

/**
 * Number of broken builds per user.
 * 
 * @author sleroy
 */
public class NumberOfBrokenBuildPerUserPerDay extends AbstractCEPQueryImplementation {

	/**
     * 
     */
	public NumberOfBrokenBuildPerUserPerDay() {

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
		        .chain(new WithUserFilter()).chain(new EventTypeFilter("build_broken")).build();
		final IFilterDefinition filterDefinition = FilterDefinition.create()
		        .setCacheConfiguration(buildExpirationCache()).setFilter(eventFilter)
		        .setFilterName("jenkins-brokenbuilder-filter");

		return Collections.singletonList(filterDefinition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.product.cep.api.ICEPQueryImplementation#getFormula()
	 */
	@Override
	public ICEPFormula<?, KpiResult> getFormula() {

		return new UserFormula(new EventCountFormula());
	}

}
