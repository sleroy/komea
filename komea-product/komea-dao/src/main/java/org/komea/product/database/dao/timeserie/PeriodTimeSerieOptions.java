
package org.komea.product.database.dao.timeserie;



import java.util.Date;

import org.joda.time.DateTime;



public class PeriodTimeSerieOptions extends TimeSerieOptions
{
    
    
    private Date fromPeriod;
    
    
    private Date toPeriod;
    
    
    
    @Override
    public boolean equals(final Object obj) {
    
    
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof PeriodTimeSerieOptions)) {
            return false;
        }
        final PeriodTimeSerieOptions other = (PeriodTimeSerieOptions) obj;
        if (fromPeriod == null) {
            if (other.fromPeriod != null) {
                return false;
            }
        } else if (!fromPeriod.equals(other.fromPeriod)) {
            return false;
        }
        if (toPeriod == null) {
            if (other.toPeriod != null) {
                return false;
            }
        } else if (!toPeriod.equals(other.toPeriod)) {
            return false;
        }
        return true;
    }
    
    
    public Date getFromPeriod() {
    
    
        return fromPeriod;
    }
    
    
    @Override
    public GroupFormula getGroupFormula() {
    
    
        return groupFormula;
    }
    
    
    @Override
    public int getKpiID() {
    
    
        return kpiID;
    }
    
    
    @Override
    public TimeScale getTimeScale() {
    
    
        return timeScale;
    }
    
    
    public Date getToPeriod() {
    
    
        return toPeriod;
    }
    
    
    @Override
    public int hashCode() {
    
    
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (fromPeriod == null ? 0 : fromPeriod.hashCode());
        result = prime * result + (toPeriod == null ? 0 : toPeriod.hashCode());
        return result;
    }
    
    
    public boolean isPerYear() {
    
    
        return timeScale == TimeScale.PER_YEAR;
    }
    
    
    /**
     * @return
     */
    @Override
    public boolean isValid() {
    
    
        return super.isValid() && fromPeriod != null && toPeriod != null;
    }
    
    
    public void lastYears(final int _numberOfYears) {
    
    
        fromPeriod = new DateTime().minusYears(_numberOfYears).toDate();
        
    }
    
    
    public void setFromPeriod(final DateTime _fromPeriod) {
    
    
        fromPeriod = _fromPeriod.toDate();
    }
    
    
    @Override
    public void setGroupFormula(final GroupFormula _groupFormula) {
    
    
        groupFormula = _groupFormula;
    }
    
    
    @Override
    public void setKpiID(final int _kpiID) {
    
    
        kpiID = _kpiID;
    }
    
    
    @Override
    public void setTimeScale(final TimeScale _timeScale) {
    
    
        timeScale = _timeScale;
    }
    
    
    public void setToPeriod(final DateTime _toPeriod) {
    
    
        toPeriod = _toPeriod.toDate();
    }
    
    
    @Override
    public String toString() {
    
    
        return "PeriodTimeSerieOptions [fromPeriod="
                + fromPeriod + ", toPeriod=" + toPeriod + ", groupFormula=" + groupFormula
                + ", kpiID=" + kpiID + ", timeScale=" + timeScale + "]";
    }
    
    
    /**
     * Sets the toPeriod to now.
     */
    public void untilNow() {
    
    
        toPeriod = new DateTime().toDate();
        
    }
}
