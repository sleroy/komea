
package org.komea.product.service.dto;


import java.util.List;

import org.komea.product.database.model.Measure;

import com.google.common.collect.Lists;

public class MeasureHistoricalResultDto {
    
    /**
     * the kpi associate to the measure
     */
    private KpiKey        kpiKey;
    
    /**
     * the asked measure
     */
    private List<Measure> measure;
    
    public MeasureHistoricalResultDto() {
    
        measure = Lists.newArrayList();
    }
    
    public MeasureHistoricalResultDto(final KpiKey _kpiKey) {
    
        kpiKey = _kpiKey;
        measure = Lists.newArrayList();
    }
    
    public KpiKey getKpiKey() {
    
        return kpiKey;
    }
    
    public void setKpiKey(final KpiKey _kpiKey) {
    
        kpiKey = _kpiKey;
    }
    
    public List<Measure> getMeasure() {
    
        return measure;
    }
    
    public void setMeasure(final List<Measure> _measure) {
    
        measure = _measure;
    }
    
}
