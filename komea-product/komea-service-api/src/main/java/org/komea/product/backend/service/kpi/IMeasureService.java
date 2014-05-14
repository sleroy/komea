
package org.komea.product.backend.service.kpi;


import java.util.List;

import org.komea.product.service.dto.KpiStringKey;
import org.komea.product.service.dto.KpiStringKeyList;
import org.komea.product.service.dto.MeasureResult;

public interface IMeasureService {
    
    List<MeasureResult> currentMeasures(KpiStringKeyList _kpiKeys);
    
    double currentMeasure(KpiStringKey _kpiKey);
    
    //
    
}
