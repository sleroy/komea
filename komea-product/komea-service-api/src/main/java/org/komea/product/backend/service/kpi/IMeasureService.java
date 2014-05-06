
package org.komea.product.backend.service.kpi;


import java.util.List;

public interface IMeasureService {
    
    //
    
    MeasureResult getMeasure(final HistoryStringKey _historyKey, final LimitCriteria _limit);
    
    List<MeasureResult> getMeasures(final HistoryStringKeyList _historyKeyList, final LimitCriteria _limit);
}
