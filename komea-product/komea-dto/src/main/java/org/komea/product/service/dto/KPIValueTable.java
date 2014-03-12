
package org.komea.product.service.dto;



import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.model.Kpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * KpiValueTable : kpi value table.
 */
public class KPIValueTable<T extends IEntity> implements Serializable
{
    
    
    private static final Logger    LOGGER           = LoggerFactory.getLogger("kpi-results");
    /**
     * 
     */
    private static final long      serialVersionUID = -5540905818003613307L;
    private Kpi                    kpi;
    
    
    private Map<EntityKey, Double> values           = null;
    
    
    
    public KPIValueTable() {
    
    
        super();
    }
    
    
    /**
     * @param _kpi
     * @param _map
     */
    public KPIValueTable(final Kpi _kpi, final Map<EntityKey, Double> _map) {
    
    
        super();
        kpi = _kpi;
        values = _map;
    }
    
    
    /**
     * Add a measure
     * 
     * @param _entity
     *            the entity
     * @param _double
     *            the value.
     */
    public void addMeasure(final IEntity _entity, final Double _double) {
    
    
        Validate.notNull(_entity.getEntityKey());
        Validate.notNull(_double);
        
        this.values.put(_entity.getEntityKey(), _double);
        
    }
    
    
    /**
     * Converts the table into a map.
     * 
     * @return convert the table into a map.
     */
    public Map<EntityKey, Double> convertMap() {
    
    
        return values;
    }
    
    
    /**
     * Dump Informations
     */
    public void dump() {
    
    
        LOGGER.info("Results of {} are  : {}", kpi, this.values);
        
    }
    
    
    /**
     * @return the kpi
     */
    public Kpi getKpi() {
    
    
        return kpi;
    }
    
    
    /**
     * @return
     */
    @JsonIgnore
    public int getNumberOfRecords() {
    
    
        return this.values.size();
    }
    
    
    /**
     * Returns the entity
     * 
     * @param _entity
     *            the entity.
     * @return the entity value.
     */
    public Double getValueOfEntity(final IEntity _entity) {
    
    
        LOGGER.info("getValueOfEntity " + _entity);
        final Object object = values.get(_entity.getEntityKey());
        
        if (object == null) { return 0.0d; }
        return ((Number) object).doubleValue();
    }
    
    
    /**
     * @param _kpi
     *            the kpi to set
     */
    public void setKpi(final Kpi _kpi) {
    
    
        kpi = _kpi;
    }
    
    
}
