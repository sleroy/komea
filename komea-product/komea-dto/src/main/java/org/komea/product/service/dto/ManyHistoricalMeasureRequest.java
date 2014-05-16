
package org.komea.product.service.dto;


import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.komea.product.model.timeserie.PeriodTimeSerieOptions;

@JsonAutoDetect
public class ManyHistoricalMeasureRequest {
    
    private KpiStringKeyList       kpiKeyList;
    private PeriodTimeSerieOptions period;
    
    public ManyHistoricalMeasureRequest() {
    
        kpiKeyList = new KpiStringKeyList();
        period = new PeriodTimeSerieOptions();
    }
    
    public ManyHistoricalMeasureRequest(final KpiStringKeyList _kpiKeyList, final PeriodTimeSerieOptions _period) {
    
        super();
        kpiKeyList = _kpiKeyList;
        period = _period;
    }
    
    public KpiStringKeyList getKpiKeyList() {
    
        return kpiKeyList;
    }
    
    public PeriodTimeSerieOptions getPeriod() {
    
        return period;
    }
    
    public void setKpiKeyList(final KpiStringKeyList _kpiKeyList) {
    
        kpiKeyList = _kpiKeyList;
    }
    
    public void setPeriod(final PeriodTimeSerieOptions _period) {
    
        period = _period;
    }
    
}
