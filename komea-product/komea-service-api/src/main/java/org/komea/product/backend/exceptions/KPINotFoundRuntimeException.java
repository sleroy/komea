
package org.komea.product.backend.exceptions;



import org.komea.product.database.api.IEntity;



/**
 */
public class KPINotFoundRuntimeException extends KomeaException
{
    
    
    private final IEntity entity;
    private final String  kpiName;
    
    
    
    public KPINotFoundRuntimeException() {
    
    
        super("KPI was not found ");
        entity = null;
        kpiName = null;
    }
    
    
    /**
     * Constructor for KPINotFoundRuntimeException.
     * 
     * @param _entity
     *            IEntity
     * @param _kpiName
     *            String
     */
    public KPINotFoundRuntimeException(final IEntity _entity, final String _kpiName) {
    
    
        super("KPI was not found " + _kpiName);
        entity = _entity;
        kpiName = _kpiName;
        
    }
    
    
    /**
     * Constructor for KPINotFoundRuntimeException.
     * 
     * @param _entity
     *            IEntity
     * @param _kpiName
     *            String
     * @param _e
     *            Throwable
     */
    public KPINotFoundRuntimeException(
            final IEntity _entity,
            final String _kpiName,
            final Throwable _e) {
    
    
        super("KPI was not found " + _kpiName, _e);
        entity = _entity;
        kpiName = _kpiName;
        
    }
    
    
    /**
     * @param _kpiKey
     */
    public KPINotFoundRuntimeException(final Integer _kpiKey) {
    
    
        this(null, "pkey=" + _kpiKey, null);
    }
    
    
    /**
     * Constructor for KPINotFoundRuntimeException.
     * 
     * @param _kpiName
     *            String
     */
    public KPINotFoundRuntimeException(final String _kpiName) {
    
    
        this(null, _kpiName, null);
        
    }
    
    
    /**
     * Method getEntity.
     * 
     * @return Object
     */
    public Object getEntity() {
    
    
        return entity;
    }
    
    
    /**
     * Method getKpiName.
     * 
     * @return String
     */
    public String getKpiName() {
    
    
        return kpiName;
    }
    
}
