
package org.komea.product.database.dao.timeserie;



public class TimeCoordinate
{
    
    
    public int getDay() {
    
    
        return day;
    }
    
    
    public void setDay(int _day) {
    
    
        day = _day;
    }
    
    
    public int getHour() {
    
    
        return hour;
    }
    
    
    public void setHour(int _hour) {
    
    
        hour = _hour;
    }
    
    
    public int getMonth() {
    
    
        return month;
    }
    
    
    public void setMonth(int _month) {
    
    
        month = _month;
    }
    
    
    public Double getValue() {
    
    
        return value;
    }
    
    
    public void setValue(Double _value) {
    
    
        value = _value;
    }
    
    
    public int getWeek() {
    
    
        return week;
    }
    
    
    public void setWeek(int _week) {
    
    
        week = _week;
    }
    
    
    public int getYear() {
    
    
        return year;
    }
    
    
    public void setYear(int _year) {
    
    
        year = _year;
    }
    
    
    @Override
    public String toString() {
    
    
        return "TimeCoordinate [day="
                + day + ", hour=" + hour + ", month=" + month + ", value=" + value + ", week="
                + week + ", year=" + year + "]";
    }
    
    
    
    private int    day;
    private int    hour;
    private int    month;
    private Double value;
    private int    week;
    
    private int    year;
}
