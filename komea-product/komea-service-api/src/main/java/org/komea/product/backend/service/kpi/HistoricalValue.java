
package org.komea.product.backend.service.kpi;


import java.util.Date;

public class HistoricalValue {
    
    //
    
    private Double value;
    private Date   measureDate;
    
    public HistoricalValue() {
    
        // TODO Auto-generated HistoricalValue stub
    }
    
    public HistoricalValue(final Double _value, final Date _measureDate) {
    
        super();
        value = _value;
        measureDate = _measureDate;
    }
    
    public Double getValue() {
    
        return value;
    }
    
    public void setValue(final Double _value) {
    
        value = _value;
    }
    
    public Date getMeasureDate() {
    
        return measureDate;
    }
    
    public void setMeasureDate(final Date _measureDate) {
    
        measureDate = _measureDate;
    }
    
}
