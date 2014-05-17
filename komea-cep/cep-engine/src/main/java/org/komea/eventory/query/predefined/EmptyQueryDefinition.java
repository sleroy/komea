/**
 * 
 */

package org.komea.eventory.query.predefined;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.eventory.api.filters.IFilterDefinition;
import org.komea.eventory.api.formula.ICEPFormula;
import org.komea.eventory.formula.CountFormula;

/**
 * Provides a stub for query definition.
 * 
 * @author sleroy
 */
public class EmptyQueryDefinition implements ICEPQueryImplementation {

	@Override
	public BackupDelay getBackupDelay() {

		return BackupDelay.DAY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.eventory.api.ICEPQueryImplementation#getFilterDefinitions()
	 */
	@Override
	public List<IFilterDefinition> getFilterDefinitions() {

		return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.eventory.api.ICEPQueryImplementation#getFormula()
	 */
	@Override
	public ICEPFormula getFormula() {

		return new CountFormula<Serializable>();
	}

}
