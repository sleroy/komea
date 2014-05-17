/**
 * 
 */

package org.komea.eventory.query;

import java.util.ArrayList;
import java.util.List;

import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.eventory.api.filters.IFilterDefinition;
import org.komea.eventory.api.formula.ICEPFormula;

/**
 * This class defines the implementation necessary to instantiate a cep query
 * 
 * @author sleroy
 */
public class CEPQueryImplementation implements ICEPQueryImplementation {

	private final List<IFilterDefinition>	filterDefinitions	= new ArrayList<IFilterDefinition>();

	private ICEPFormula	                  formula;

	private BackupDelay	                  backupDelay	      = BackupDelay.DAY;

	/**
	 * @param _queryName
	 *            the query name.
	 */
	public CEPQueryImplementation() {

		super();
	}

	/**
	 * @param _filterDefinition
	 */
	public void addFilterDefinition(final IFilterDefinition _filterDefinition) {

		filterDefinitions.add(_filterDefinition);

	}

	@Override
	public BackupDelay getBackupDelay() {

		return backupDelay;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.eventory.api.ICEPQueryImplementation#getFilterDefinitions()
	 */
	@Override
	public List<IFilterDefinition> getFilterDefinitions() {

		return filterDefinitions;
	}

	/**
	 * @return the formula
	 */
	@Override
	public ICEPFormula getFormula() {

		return formula;
	}

	public void setBackupDelay(final BackupDelay _backupDelay) {
		backupDelay = _backupDelay;
	}

	/**
	 * @param _formula
	 *            the formula to set
	 */
	public void setFormula(final ICEPFormula _formula) {

		formula = _formula;
	}

	@Override
	public String toString() {
		return "CEPQueryImplementation [filterDefinitions=" + filterDefinitions + ", formula=" + formula
		        + ", backupDelay=" + backupDelay + "]";
	}

}
