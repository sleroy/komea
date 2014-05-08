
package org.komea.product.database.dao.timeserie;



public class TimeCoordinate
{
    
    
    private int    day;
    
    
    private int    hour;
    
    
    private int    month;
    
    
    private Double value;
    
    
    private int    week;
    
    
    private int    year;
    
    
    
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
