
package org.komea.product.service.dto;


import org.komea.product.database.model.Measure;

/**
 * Komea dto to return a measure associate to a kpi
 * <p>
 * 
 * @author $Author: jguidoux $
 * @since 8 févr. 2014
 */
public class MeasureResultDto {
    
    //
    
    /**
     * the kpi associate to the measure
     */
    private KpiKey  kpiKey;
    
    /**
     * the asked measure
     */
    private Measure measure;
    
    public MeasureResultDto() {
    
        // TODO Auto-generated MeasureResultDto stub
    }
    
    public MeasureResultDto(final KpiKey _kpiKey, final Measure _measure) {
    
        super();
        kpiKey = _kpiKey;
        measure = _measure;
    }
    public KpiKey getKpiKey() {
    
        return kpiKey;
    }
    
    public Measure getMeasure() {
    
        return measure;
    }
    
    public void setKpiKey(final KpiKey _kpiKey) {
    
        kpiKey = _kpiKey;
    }
    
    public void setMeasure(final Measure _measure) {
    
        measure = _measure;
    }
    
}
