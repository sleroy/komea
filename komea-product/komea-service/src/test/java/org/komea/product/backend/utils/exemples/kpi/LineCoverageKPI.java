package org.komea.product.backend.utils.exemples.kpi;

import java.util.Collections;
import java.util.List;

import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.eventory.api.filters.IFilterDefinition;
import org.komea.eventory.api.formula.ICEPFormula;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.plugins.kpi.formula.ProjectFormula;
import org.komea.product.plugins.kpi.standard.EventValueFormula;

public class LineCoverageKPI implements ICEPQueryImplementation {

	private final String	metricName;

	/**
     * 
     */
	public LineCoverageKPI(final String _metricName) {

		super();
		metricName = _metricName;
	}

	@Override
	public BackupDelay getBackupDelay() {

		return BackupDelay.DAY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.product.cep.api.ICEPQueryImplementation#getFilterDefinitions()
	 */
	@Override
	public List<IFilterDefinition> getFilterDefinitions() {

		return Collections.emptyList();
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
