
package org.komea.product.backend.esper.reactor;



public class KPINotFoundRuntimeException extends RuntimeException
{
    
    
    private final Object entity;
    private final String kpiName;
    
    
    
    public KPINotFoundRuntimeException(
            final Object _entity,
            final String _kpiName,
            final Throwable _e) {
    
    
        super("KPI was not found " + _kpiName, _e);
        entity = _entity;
        kpiName = _kpiName;
        
        
    }
    
    
    public Object getEntity() {
    
    
        return entity;
    }
    
    
    public String getKpiName() {
    
    
        return kpiName;
    }
    
}
