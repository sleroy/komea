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
    
    
    public GroupFormula getGroupFormula() {
    
    
        return groupFormula;
    }
    
    
    public int getKpiID() {
    
    
        return kpiID;
    }
    
    
    public TimeScale getTimeScale() {
    
    
        return timeScale;
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
