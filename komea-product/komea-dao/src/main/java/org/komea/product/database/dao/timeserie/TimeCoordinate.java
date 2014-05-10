
package org.komea.product.database.dao.timeserie;



public class TimeCoordinate
{
    
    
    private int    day;
    
    
    private int    hour;
    
    
    private int    month;
    
    
    private Double value;
    
    
    private int    week;
    
    
    private int    year;
    
    
    
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
        if (hour != other.hour) {
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
    
    
    public int getHour() {
    
    
        return hour;
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
    
    
    @Override
    public int hashCode() {
    
    
        final int prime = 31;
        int result = 1;
        result = prime * result + day;
        result = prime * result + hour;
        result = prime * result + month;
        result = prime * result + (value == null ? 0 : value.hashCode());
        result = prime * result + week;
        result = prime * result + year;
        return result;
    }
    
    
    public void setDay(final int _day) {
    
    
        day = _day;
    }
    
    
    public void setHour(final int _hour) {
    
    
        hour = _hour;
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
    
    
    @Override
    public String toString() {
    
    
        return "TimeCoordinate [day="
                + day + ", hour=" + hour + ", month=" + month + ", value=" + value + ", week="
                + week + ", year=" + year + "]";
    }
}
