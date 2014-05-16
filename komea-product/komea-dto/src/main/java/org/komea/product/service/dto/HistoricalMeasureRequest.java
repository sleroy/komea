
package org.komea.product.service.dto;


import org.komea.product.model.timeserie.PeriodTimeSerieOptions;

public class HistoricalMeasureRequest {
    
    private KpiStringKey           kpiKey;
    private PeriodTimeSerieOptions period;
    
    public HistoricalMeasureRequest() {
    
        // TODO Auto-generated HistoricalMeasureRequest stub
    }
    
    public HistoricalMeasureRequest(final KpiStringKey _kpiKey, final PeriodTimeSerieOptions _period) {
    
        super();
        kpiKey = _kpiKey;
        period = _period;
    }
    
    public KpiStringKey getKpiKey() {
    
        return kpiKey;
    }
    
    public PeriodTimeSerieOptions getPeriod() {
    
        return period;
    }
    
    public void setKpiKey(final KpiStringKey _kpiKey) {
    
        kpiKey = _kpiKey;
    }
    
    public void setPeriod(final PeriodTimeSerieOptions _period) {
    
        period = _period;
    }
    
}
