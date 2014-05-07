
package org.komea.product.backend.service.kpi;


import org.komea.product.backend.api.IHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeasureService implements IMeasureService {
    
    @Autowired
    private IHistoryService historyService;
    
    //
}
