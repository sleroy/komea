
package org.komea.product.backend.utils.exemples.kpi;


import org.komea.product.cep.api.dynamicdata.IDynamicDataQuery;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.EntityType;
import org.komea.product.service.dto.EntityKey;

public class BranchCoverageKPI implements IDynamicDataQuery {
    
    @Override
    public String getFormula() {
    
        // TODO Auto-generated getFormula
        return null;
    }
    
    @Override
    public KpiResult getResult() {
    
        KpiResult kpiResult = new KpiResult();
        kpiResult.put(EntityKey.of(EntityType.PROJECT, 4), 62);
        return kpiResult;
    }
}
