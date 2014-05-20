
package org.komea.product.backend.service.kpi;



import java.util.List;

import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Kpi;



public class InferMissingEntityValuesIntoKpiResult
{
    
    
    private final IEntityService entityService;
    private final Kpi            kpi;
    private final KpiResult      result;
    
    
    
    public InferMissingEntityValuesIntoKpiResult(
            final KpiResult _result,
            final Kpi _kpi,
            final IEntityService _entityService) {
    
    
        super();
        result = _result;
        kpi = _kpi;
        entityService = _entityService;
    }
    
    
    /**
     * Infer vacant entities into the kpi result;
     */
    public KpiResult inferEntityKeys() {
    
    
        final List<IEntity> byEntityType =
                entityService.getEntitiesByEntityType(kpi.getEntityType());
        return result.inferResults(byEntityType, null);
    }
    
}
