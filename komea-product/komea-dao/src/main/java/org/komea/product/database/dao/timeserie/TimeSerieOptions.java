/**
 * 
 */

package org.komea.product.database.dao.timeserie;



import java.io.Serializable;



/**
 * @author sleroy
 */
public class TimeSerieOptions implements Serializable
{
    
    
    protected GroupFormula groupFormula;
    
    
    protected int          kpiID;
    
    
    protected TimeScale    timeScale;
    
    
    
    /**
     * 
     */
    public TimeSerieOptions() {
    
    
        super();
    }
    
    
    @Override
    public boolean equals(final Object obj) {
    
    
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof TimeSerieOptions)) {
            return false;
        }
        final TimeSerieOptions other = (TimeSerieOptions) obj;
        if (groupFormula != other.groupFormula) {
            return false;
        }
        if (kpiID != other.kpiID) {
            return false;
        }
        if (timeScale != other.timeScale) {
            return false;
        }
        return true;
    }
    
    
    public GroupFormula getGroupFormula() {
    
    
        return groupFormula;
    }
    
    
    public int getKpiID() {
    
    
        return kpiID;
    }
    
    
    public TimeScale getTimeScale() {
    
    
        return timeScale;
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
    
    
    /**
     * @return
     */
    public boolean isValid() {
    
    
        return groupFormula != null && timeScale != null;
    }
    
    
    public void setGroupFormula(final GroupFormula _groupFormula) {
    
    
        groupFormula = _groupFormula;
    }
    
    
    public void setKpiID(final int _kpiID) {
    
    
        kpiID = _kpiID;
    }
    
    
    public void setTimeScale(final TimeScale _timeScale) {
    
    
        timeScale = _timeScale;
    }
    
    
    @Override
    public String toString() {
    
    
        return "TimeSerieOptions [groupFormula="
                + groupFormula + ", kpiID=" + kpiID + ", timeScale=" + timeScale + "]";
    }
    
}
