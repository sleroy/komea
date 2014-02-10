
package org.komea.product.database.dto;



import org.komea.product.service.dto.KpiKey;



public class KpiTendancyDto
{
    
    
    private double previousValue;
    
    
    private double actualValue;
    
    private KpiKey kpiKey;
    
    
    
    /**
     * @param _previousValue
     * @param _actualValue
     * @param _kpiKey
     */
    public KpiTendancyDto(
            final double _previousValue,
            final double _actualValue,
            final KpiKey _kpiKey) {
    
    
        super();
        previousValue = _previousValue;
        actualValue = _actualValue;
        kpiKey = _kpiKey;
    }
    
    
    public double getActualValue() {
    
    
        return actualValue;
    }
    
    
    public KpiKey getKpiKey() {
    
    
        return kpiKey;
    }
    
    
    public double getPreviousValue() {
    
    
        return previousValue;
    }
    
    
    public void setActualValue(final double _actualValue) {
    
    
        actualValue = _actualValue;
    }
    
    
    public void setKpiKey(final KpiKey _kpiKey) {
    
    
        kpiKey = _kpiKey;
    }
    
    
    public void setPreviousValue(final double _previousValue) {
    
    
        previousValue = _previousValue;
    }
}
