
package org.komea.product.functional.test;



import org.komea.eventory.api.cache.BackupDelay;
import org.komea.product.backend.groovy.AbstractGroovyQuery;
import org.komea.product.backend.service.kpi.IEntityKpiFormula;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.EntityType;
import org.komea.product.service.dto.EntityKey;



class KpiQuery extends AbstractGroovyQuery
{
    
    
    public KpiQuery() {
    
    
        super();
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
    
    
        return forEachEntity(EntityType.PERSON, new IEntityKpiFormula()
        {
            
            
            @Override
            public Number evaluate(final EntityKey _entityKey) {
            
            
                return kpi("DEMO_KPI_GROOVY1").dailyValue(_entityKey)
                        + kpi("DEMO_KPI_GROOVY2").dailyValue(_entityKey);
            }
        });
    }
        
    
}


new KpiQuery();
