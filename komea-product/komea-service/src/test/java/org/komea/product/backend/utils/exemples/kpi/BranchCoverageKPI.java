
package org.komea.product.backend.utils.exemples.kpi;


import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.IDynamicDataQuery;
import org.komea.product.backend.groovy.AbstractGroovyQuery;
import org.komea.product.backend.service.kpi.IEntityKpiFormula;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.EntityType;
import org.komea.product.service.dto.EntityKey;

public class BranchCoverageKPI extends AbstractGroovyQuery implements IEntityKpiFormula, IDynamicDataQuery<KpiResult> {
    
    public BranchCoverageKPI() {
    
        super(EntityType.PROJECT);
    }
    
    @Override
    public Number evaluate(final EntityKey _entityKey) {
    
        return 62;
    }
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.engine.IQuery#getBackupDelay()
     */
    @Override
    public BackupDelay getBackupDelay() {
    
        return BackupDelay.DAY;
    }
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.engine.IQuery#getResult()
     */
    @Override
    public KpiResult getResult() {
    
        return forEachEntity(this);
    }
    
}
