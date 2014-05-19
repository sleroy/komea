/**
 * 
 */

package org.komea.product.plugins.scm.kpi;

import java.util.Collections;
import java.util.List;

import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.filters.IFilterDefinition;
import org.komea.eventory.api.formula.ICEPFormula;
import org.komea.eventory.filter.EventFilterBuilder;
import org.komea.product.plugins.kpi.standard.bugzilla.AbstractCEPQueryImplementation;
import org.komea.product.plugins.scm.api.plugin.ICommitFunction;

/**
 * @author sleroy
 */
public abstract class ScmUserQueryImplementation extends AbstractCEPQueryImplementation {

	public ScmUserQueryImplementation() {
		super(BackupDelay.DAY);

	}

	/**
	 * Returns the commit function.
	 * 
	 * @return the commit function.
	 */
	public abstract ICommitFunction getCommitFunction();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.eventory.api.engine.ICEPQueryImplementation#getFilterDefinitions
	 * ()
	 */
	@Override
	public List<IFilterDefinition> getFilterDefinitions() {

		return Collections.singletonList(EventFilterBuilder.create().chain(new ScmCommitFilter())
		        .buildFilterDefinition("scmcommit-filter", buildExpirationCache()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.eventory.api.engine.ICEPQueryImplementation#getFormula()
	 */
	@Override
	public ICEPFormula getFormula() {

		return new UserCommitFormula(getCommitFunction());
	}

}
