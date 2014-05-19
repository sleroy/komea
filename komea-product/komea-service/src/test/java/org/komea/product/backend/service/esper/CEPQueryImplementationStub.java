/**
 * 
 */

package org.komea.product.backend.service.esper;

import java.io.Serializable;
import java.util.List;

import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.eventory.api.filters.IFilterDefinition;
import org.komea.eventory.api.formula.ICEPFormula;
import org.komea.eventory.filter.NoEventFilter;
import org.komea.eventory.formula.CountFormula;
import org.komea.eventory.query.FilterDefinition;

import com.google.common.collect.Lists;

/**
 * @author sleroy
 */
public class CEPQueryImplementationStub implements ICEPQueryImplementation {

	@Override
	public BackupDelay getBackupDelay() {

		return BackupDelay.DAY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.eventory.api.engine.ICEPQueryImplementation#getFilterDefinitions
	 * ()
	 */
	@Override
	public List<IFilterDefinition> getFilterDefinitions() {

		final IFilterDefinition setFilterName = FilterDefinition.create().setFilter(new NoEventFilter())
		        .setFilterName("gloups");
		return Lists.newArrayList(setFilterName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.eventory.api.engine.ICEPQueryImplementation#getFormula()
	 */
	@Override
	public ICEPFormula getFormula() {

		return new CountFormula<Serializable>();
	}

}
