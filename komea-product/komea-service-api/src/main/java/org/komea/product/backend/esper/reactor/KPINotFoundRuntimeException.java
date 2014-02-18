
package org.komea.product.backend.esper.reactor;



import org.komea.product.database.api.IEntity;



public class KPINotFoundRuntimeException extends RuntimeException
{
    
    
    private final IEntity entity;
    private final String  kpiName;
    
    
    
    public KPINotFoundRuntimeException() {
    
    
        super("KPI was not found ");
        entity = null;
        kpiName = null;
    }
    
    
    public KPINotFoundRuntimeException(final IEntity _entity, final String _kpiName) {
    
    
        super("KPI was not found " + _kpiName);
        entity = _entity;
        kpiName = _kpiName;
        
    }
    
    
    public KPINotFoundRuntimeException(
            final IEntity _entity,
            final String _kpiName,
            final Throwable _e) {
    
    
        super("KPI was not found " + _kpiName, _e);
        entity = _entity;
        kpiName = _kpiName;
        
    }
    
    
    public KPINotFoundRuntimeException(final String _kpiName) {
    
    
        kpiName = _kpiName;
        entity = null;
    }
    
    
    public Object getEntity() {
    
    
        return entity;
    }
    
    
    public String getKpiName() {
    
    
        return kpiName;
    }
    
}
