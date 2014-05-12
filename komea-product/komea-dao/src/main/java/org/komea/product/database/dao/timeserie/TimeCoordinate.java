
package org.komea.product.database.dao.timeserie;



public class TimeCoordinate
{
    
    
    private int    day;
    
    
    private int    entityID;
    
    
    private int    hour;
    
    
    private int    kpiID;
    
    
    private int    month;
    
    
    private Double value;
    
    private int    week;
    
    
    private int    year;
    
    
    
    public TimeCoordinate() {
    
    
        super();
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
        final TimeCoordinate other = (TimeCoordinate) obj;
        if (day != other.day) {
            return false;
        }
        if (entityID != other.entityID) {
            return false;
        }
        if (hour != other.hour) {
            return false;
        }
        if (kpiID != other.kpiID) {
            return false;
        }
        if (month != other.month) {
            return false;
        }
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        if (week != other.week) {
            return false;
        }
        if (year != other.year) {
            return false;
        }
        return true;
    }
    
    
    public int getDay() {
    
    
        return day;
    }
    
    
    /**
     * Returns the value of the field entityID.
     * 
     * @return the entityID
     */
    public int getEntityID() {
    
    
        return entityID;
    }
    
    
    public int getHour() {
    
    
        return hour;
    }
    
    
    /**
     * Returns the value of the field kpiID.
     * 
     * @return the kpiID
     */
    public int getKpiID() {
    
    
        return kpiID;
    }
    
    
    public int getMonth() {
    
    
        return month;
    }
    
    
    public Double getValue() {
    
    
        return value;
    }
    
    
    public int getWeek() {
    
    
        return week;
    }
    
    
    public int getYear() {
    
    
        return year;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
    
    
        final int prime = 31;
        int result = 1;
        result = prime * result + day;
        result = prime * result + entityID;
        result = prime * result + hour;
        result = prime * result + kpiID;
        result = prime * result + month;
        result = prime * result + (value == null ? 0 : value.hashCode());
        result = prime * result + week;
        result = prime * result + year;
        return result;
    }
    
    
    public void setDay(final int _day) {
    
    
        day = _day;
    }
    
    
    /**
     * Sets the field entityID with the value of _entityID.
     * 
     * @param _entityID
     *            the entityID to set
     */
    public void setEntityID(final int _entityID) {
    
    
        entityID = _entityID;
    }
    
    
    public void setHour(final int _hour) {
    
    
        hour = _hour;
    }
    
    
    /**
     * Sets the field kpiID with the value of _kpiID.
     * 
     * @param _kpiID
     *            the kpiID to set
     */
    public void setKpiID(final int _kpiID) {
    
    
        kpiID = _kpiID;
    }
    
    
    public void setMonth(final int _month) {
    
    
        month = _month;
    }
    
    
    public void setValue(final Double _value) {
    
    
        value = _value;
    }
    
    
    public void setWeek(final int _week) {
    
    
        week = _week;
    }
    
    
    public void setYear(final int _year) {
    
    
        year = _year;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "TimeCoordinate [day="
                + day + ", entityID=" + entityID + ", hour=" + hour + ", kpiID=" + kpiID
                + ", month=" + month + ", value=" + value + ", week=" + week + ", year=" + year
                + "]";
    }
}
