
package org.komea.product.model.timeserie.dto;


import java.util.Date;

public class TimeCoordinateDTO {
    
    //
    
    private Date   date;
    private double value;
    
    public TimeCoordinateDTO() {
    
        // TODO Auto-generated TimeCoordinateDTO stub
    }
    
    public TimeCoordinateDTO(final Date _date, final double _value) {
    
        super();
        date = _date;
        value = _value;
    }
    
    public Date getDate() {
    
        return date;
    }
    
    public void setDate(final Date _date) {
    
        date = _date;
    }
    
    public double getValue() {
    
        return value;
    }
    
    public void setValue(final double _value) {
    
        value = _value;
    }
    
}
