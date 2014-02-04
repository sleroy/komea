
package org.komea.product.backend.esper.reactor;



import org.komea.product.backend.service.business.IEntityWithKPI;



public class KPINotFoundException extends Exception
{
    
    
    public KPINotFoundException(final IEntityWithKPI<?> _entity, final String _kpiName) {
    
    
        super("KPI was not found for entity " + _entity + " : " + _kpiName);
        
    }
    
}
