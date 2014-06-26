/**
 *
 */

package org.komea.product.model.timeserie;



import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.komea.product.database.enums.GroupFormula;
import org.komea.product.database.model.Kpi;
import org.slf4j.LoggerFactory;



/**
 * @author sleroy
 */
public class TimeSerieOptions implements Serializable
{
    
    
    protected static final org.slf4j.Logger LOGGER = LoggerFactory
                                                           .getLogger(PeriodTimeSerieOptions.class);

    private String                          uniqueID;
    
    protected GroupFormula                  groupFormula;
    @NotNull
    protected Integer                       kpiID;
    
    @NotNull
    protected TimeScale                     timeScale;
    
    
    
    public TimeSerieOptions() {
    
    
        super();
    }
    
    
    /**
     * @param _kpi
     */
    public TimeSerieOptions(final Kpi _kpi) {
    
    
        super();
        kpiID = _kpi.getId();
        groupFormula = _kpi.getGroupFormula();
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
    
    
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TimeSerieOptions other = (TimeSerieOptions) obj;
        if (groupFormula != other.groupFormula) {
            return false;
        }
        if (kpiID == null) {
            if (other.kpiID != null) {
                return false;
            }
        } else if (!kpiID.equals(other.kpiID)) {
            return false;
        }
        if (timeScale != other.timeScale) {
            return false;
        }
        if (uniqueID == null) {
            if (other.uniqueID != null) {
                return false;
            }
        } else if (!uniqueID.equals(other.uniqueID)) {
            return false;
        }
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
    
    
    public String getUniqueID() {
    
    
        return uniqueID;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
    
    
        final int prime = 31;
        int result = 1;
        result = prime * result + (groupFormula == null ? 0 : groupFormula.hashCode());
        result = prime * result + (kpiID == null ? 0 : kpiID.hashCode());
        result = prime * result + (timeScale == null ? 0 : timeScale.hashCode());
        result = prime * result + (uniqueID == null ? 0 : uniqueID.hashCode());
        return result;
    }
    
    
    public boolean hasKpi() {
    
    
        return kpiID != null;
    }


    public void setGroupFormula(final GroupFormula _groupFormula) {
    
    
        groupFormula = _groupFormula;
    }
    
    
    @JsonIgnore
    public void setKpi(final Kpi _kpi) {
    
    
        kpiID = _kpi.getId();
        groupFormula = _kpi.getGroupFormula();
    }
    
    
    public void setKpiID(final Integer _kpiID) {
    
    
        kpiID = _kpiID;
    }
    
    
    public void setTimeScale(final TimeScale _timeScale) {
    
    
        timeScale = _timeScale;
    }
    
    
    public void setUniqueID(final String _uniqueID) {
    
    
        uniqueID = _uniqueID;
    }
    
    
    @Override
    public String toString() {
    
    
        return "TimeSerieOptions [groupFormula="
                + groupFormula + ", kpiID=" + kpiID + ", timeScale=" + timeScale + ", uniqueID="
                + uniqueID + "]";
    }
}
