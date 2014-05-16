
package org.komea.product.service.dto;



public class ManyHistoricalMeasureRequestTemp {
    
    private KpiStringKeyList kpiKeyList;
    private PeriodCriteria   period;
    
    public ManyHistoricalMeasureRequestTemp() {
    
        kpiKeyList = new KpiStringKeyList();
        period = new PeriodCriteria();
    }
    
    public ManyHistoricalMeasureRequestTemp(final KpiStringKeyList _kpiKeyList, final PeriodCriteria _period) {
    
        super();
        kpiKeyList = _kpiKeyList;
        period = _period;
    }
    
    public KpiStringKeyList getKpiKeyList() {
    
        return kpiKeyList;
    }
    
    public PeriodCriteria getPeriod() {
    
        return period;
    }
    
    public void setKpiKeyList(final KpiStringKeyList _kpiKeyList) {
    
        kpiKeyList = _kpiKeyList;
    }
    
    public void setPeriod(final PeriodCriteria _period) {
    
        period = _period;
    }
    
}
