/**
 * 
 */

package org.komea.product.plugins.kpi.standard.jenkins;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

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
import org.komea.product.plugins.kpi.filters.WithUserFilter;
import org.komea.product.plugins.kpi.formula.UserFormula;
import org.komea.product.plugins.kpi.standard.bugzilla.AbstractCEPQueryImplementation;
import org.komea.product.plugins.kpi.standard.jenkins.formula.ProjectBuildHealthFormula;
import org.komea.product.plugins.kpi.standard.jenkins.formula.ProjectBuildHealthFormula.HealthInformations;

/**
 * Number of broken builds per user.
 * 
 * @author sleroy
 */
public class ProjectBuildHealthInfluencePerUser extends AbstractCEPQueryImplementation {

	/**
     * 
     */
	public ProjectBuildHealthInfluencePerUser() {

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

		final HashSet<String> eventSet = new HashSet<String>();
		eventSet.addAll(HealthInformations.positiveActionsSet);
		eventSet.addAll(HealthInformations.negativeActionsSet);
		final IEventFilter<?> eventFilter = EventFilterBuilder.create().chain(new OnlyEventFilter())
		        .chain(new WithUserFilter()).chain(new EventTypeFilter(eventSet)).build();
		final IFilterDefinition filterDefinition = FilterDefinition.create()
		        .setCacheConfiguration(buildExpirationCache()).setFilter(eventFilter)
		        .setFilterName("jenkins-brokenbuild-filter");

		return Collections.singletonList(filterDefinition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.product.cep.api.ICEPQueryImplementation#getFormula()
	 */
	@Override
	public ICEPFormula<IEvent, KpiResult> getFormula() {

		return new UserFormula(new ProjectBuildHealthFormula());
	}

}
