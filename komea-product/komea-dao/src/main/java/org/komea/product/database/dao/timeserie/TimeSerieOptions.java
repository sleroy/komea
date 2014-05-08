
package org.komea.product.database.dao.timeserie;



import java.util.List;

import org.joda.time.DateTime;



public class TimeSerieOptions
{
    
    
    private DateTime      fromPeriod;
    private GroupFormula  groupFormula;
    private List<Integer> idPersonGroups;
    private List<Integer> idPersons;
    private List<Integer> idProjects;
    private int           kpiID;
    
    
    private TimeScale     timeScale;
    
    
    private DateTime      toPeriod;
    
    
    
    public DateTime getFromPeriod() {
    
    
        return fromPeriod;
    }
    
    
    public GroupFormula getGroupFormula() {
    
    
        return groupFormula;
    }
    
    
    public List<Integer> getIdPersonGroups() {
    
    
        return idPersonGroups;
    }
    
    
    public List<Integer> getIdPersons() {
    
    
        return idPersons;
    }
    
    
    public List<Integer> getIdProjects() {
    
    
        return idProjects;
    }
    
    
    public int getKpiID() {
    
    
        return kpiID;
    }
    
    
    public TimeScale getTimeScale() {
    
    
        return timeScale;
    }
    
    
    public DateTime getToPeriod() {
    
    
        return toPeriod;
    }
    
    
    public void setFromPeriod(final DateTime _fromPeriod) {
    
    
        fromPeriod = _fromPeriod;
    }
    
    
    public void setGroupFormula(final GroupFormula _groupFormula) {
    
    
        groupFormula = _groupFormula;
    }
    
    
    public void setIdPersonGroups(final List<Integer> _idPersonGroups) {
    
    
        idPersonGroups = _idPersonGroups;
    }
    
    
    public void setIdPersons(final List<Integer> _idPersons) {
    
    
        idPersons = _idPersons;
    }
    
    
    public void setIdProjects(final List<Integer> _idProjects) {
    
    
        idProjects = _idProjects;
    }
    
    
    public void setKpiID(final int _kpiID) {
    
    
        kpiID = _kpiID;
    }
    
    
    public void setTimeScale(final TimeScale _timeScale) {
    
    
        timeScale = _timeScale;
    }
    
    
    public void setToPeriod(final DateTime _toPeriod) {
    
    
        toPeriod = _toPeriod;
    }
    
    
    @Override
    public String toString() {
    
    
        return "TimeSerieOptions [fromPeriod="
                + fromPeriod + ", groupFormula=" + groupFormula + ", idPersonGroups="
                + idPersonGroups + ", idPersons=" + idPersons + ", idProjects=" + idProjects
                + ", kpiID=" + kpiID + ", timeScale=" + timeScale + ", toPeriod=" + toPeriod + "]";
    }
}
