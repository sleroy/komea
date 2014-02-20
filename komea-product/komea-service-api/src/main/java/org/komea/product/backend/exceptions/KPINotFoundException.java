
package org.komea.product.backend.exceptions;



import org.komea.product.database.api.IEntity;



/**
 */
public class KPINotFoundException extends Exception
{
    
    
    /**
     * Constructor for KPINotFoundException.
     * @param _entity IEntity
     * @param _kpiName String
     */
    public KPINotFoundException(final IEntity _entity, final String _kpiName) {
    
    
        super("KPI was not found for entity " + _entity + " : " + _kpiName);
        
    }
    
}
