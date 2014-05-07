
package org.komea.product.service.dto;


import org.komea.product.database.enums.ExtendedEntityType;

/**
 * A measure is a value for a KPI to an entity
 * The type describe information to find a measire
 * <p>
 * 
 * @author $Author: jguidoux $
 * @since 6 mai 2014
 */
public class HistoryStringKey {
    
    /**
     * the kpi key
     */
    private String             KpiKey;
    
    /**
     * the entity measure with the kpi
     */
    private String             entityKey;
    
    /**
     * the kpi type : PROJECT, GROUP....
     */
    private ExtendedEntityType entityType;
    
    public HistoryStringKey() {
    
        super();
    }
    
    public HistoryStringKey(final String _kpiKey, final String _entityKey, final ExtendedEntityType _entityType) {
    
        super();
        KpiKey = _kpiKey;
        entityKey = _entityKey;
        entityType = _entityType;
    }
    
    public String getKpiKey() {
    
        return KpiKey;
    }
    
    public void setKpiKey(final String _kpiKey) {
    
        KpiKey = _kpiKey;
    }
    
    public String getEntityKey() {
    
        return entityKey;
    }
    
    public void setEntityKey(final String _entityKey) {
    
        entityKey = _entityKey;
    }
    
    public ExtendedEntityType getEntityType() {
    
        return entityType;
    }
    
    public void setEntityType(final ExtendedEntityType _entityType) {
    
        entityType = _entityType;
    }
    
}
