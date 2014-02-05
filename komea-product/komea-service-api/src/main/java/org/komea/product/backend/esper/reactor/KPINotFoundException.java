
package org.komea.product.backend.esper.reactor;



import org.komea.product.database.api.IEntity;



public class KPINotFoundException extends Exception
{
    
    
    public KPINotFoundException(final IEntity _entity, final String _kpiName) {
    
    
        super("KPI was not found for entity " + _entity + " : " + _kpiName);
        
    }
    
}
