/**
 * 
 */

package org.komea.product.model.timeserie;

import java.io.Serializable;

import org.komea.product.database.model.Kpi;

/**
 * @author sleroy
 */
public class TimeSerieOptions implements Serializable {

	protected GroupFormula	groupFormula;

	protected Integer	   kpiID;

	protected TimeScale	   timeScale;

	private int	           uniqueID;

	public TimeSerieOptions() {
		super();
	}

	/**
	 * @param _kpi
	 * 
	 */
	public TimeSerieOptions(final Kpi _kpi) {

		super();
		kpiID = _kpi.getId();
	}

	@Override
	public boolean equals(final Object obj) {

		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (!(obj instanceof TimeSerieOptions)) { return false; }
		final TimeSerieOptions other = (TimeSerieOptions) obj;
		if (groupFormula != other.groupFormula) { return false; }
		if (kpiID != other.kpiID) { return false; }
		if (timeScale != other.timeScale) { return false; }
		return true;
	}

	public GroupFormula getGroupFormula() {

		return groupFormula;
	}

	public Integer getKpiID() {
		return kpiID;
	}

	public TimeScale getTimeScale() {

		return timeScale;
	}

	public int getUniqueID() {
		return uniqueID;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + (groupFormula == null ? 0 : groupFormula.hashCode());
		result = prime * result + kpiID;
		result = prime * result + (timeScale == null ? 0 : timeScale.hashCode());
		return result;
	}

	public boolean hasKpi() {

		return kpiID != null;
	}

	/**
	 * @return
	 */
	public boolean isValid() {

		return groupFormula != null && timeScale != null && kpiID != null;
	}

	public void setGroupFormula(final GroupFormula _groupFormula) {

		groupFormula = _groupFormula;
	}

	public void setKpiID(final Integer _kpiID) {
		kpiID = _kpiID;
	}

	public void setTimeScale(final TimeScale _timeScale) {

		timeScale = _timeScale;
	}

	public void setUniqueID(final int _uniqueID) {
		uniqueID = _uniqueID;
	}

	@Override
	public String toString() {
		return "TimeSerieOptions [groupFormula=" + groupFormula + ", kpiID=" + kpiID + ", timeScale=" + timeScale
		        + ", uniqueID=" + uniqueID + "]";
	}

}
