
package org.komea.product.backend.service.kpi;


import java.util.List;

public interface IMeasureService {
    
    //
    
    MeasureResult getMeasure(final HistoryStringKey _kpiKey, final LimitCriteria _limit);
    
    List<MeasureResult> getMeasures(final List<HistoryStringKey> measureKey, final LimitCriteria _limit);
}
