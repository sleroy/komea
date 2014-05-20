/**
 * 
 */

package org.komea.product.database.dto;



import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.komea.product.database.api.IEntity;
import org.komea.product.database.enums.EntityType;
import org.komea.product.model.timeserie.EntityIdValue;
import org.komea.product.service.dto.EntityKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;



/**
 * @author sleroy
 */
public class KpiResult implements Serializable
{
    
    
    public static final KpiResult  EMPTY           = new KpiResult()
                                                   {
                                                       
                                                       
                                                       /**
                                                        * Puts a value into the map.
                                                        * 
                                                        * @param _entityKey
                                                        *            the entity key
                                                        * @param _value
                                                        *            the value;
                                                        */
                                                       @Override
                                                       public void put(
                                                               final EntityKey _entityKey,
                                                               final Number _value) {
                                                       
                                                       
                                                           throw new IllegalAccessError();
                                                           
                                                       }
                                                   };
    
    private static final Logger    LOGGER          = LoggerFactory.getLogger(KpiResult.class);
    
    
    private Map<EntityKey, Number> map             = Maps.newHashMap();
    
    
    private Throwable              reasonOfFailure = null;
    
    
    
    public KpiResult() {
    
    
        super();
    }
    
    
    public KpiResult(final Map<EntityKey, Number> _map) {
    
    
        super();
        map = _map;
        
    }
    
    
    /**
     * Fills the kpi result with an map and converts it into entity keys.
     * 
     * @param _evaluateKpiValues
     * @param _entityType
     * @return
     */
    public KpiResult fill(final List<EntityIdValue> _evaluateKpiValues, final EntityType _entityType) {
    
    
        map = Maps.newHashMapWithExpectedSize(_evaluateKpiValues.size());
        for (final EntityIdValue entry : _evaluateKpiValues) {
            map.put(EntityKey.of(_entityType, entry.getEntityID()), entry.getValue());
        }
        
        return this;
    }
    
    
    /**
     * Returns the value as a double value.
     * 
     * @param _entityKey
     *            the entity key.
     * @return the double value converted from the original value.
     */
    public Double getDoubleValue(final EntityKey _entityKey) {
    
    
        return getValue(_entityKey).doubleValue();
    }
    
    
    public Map<EntityKey, Number> getMap() {
    
    
        return Collections.unmodifiableMap(map);
    }
    
    
    public Throwable getReasonOfFailure() {
    
    
        return reasonOfFailure;
    }
    
    
    /**
     * Returns the value of an entity.
     * 
     * @param _entityKey
     *            the entity key.
     * @return the value.
     */
    public Number getValue(final EntityKey _entityKey) {
    
    
        final Number number = map.get(_entityKey);
        if (number == null) {
            return 0d;
        }
        return number;
    }
    
    
    public boolean hasFailed() {
    
    
        return reasonOfFailure != null;
    }
    
    
    /**
     * Marks the result has failed.
     * 
     * @param _reasonOfFailure
     *            the result;
     */
    public void hasFailed(final Throwable _reasonOfFailure) {
    
    
        reasonOfFailure = _reasonOfFailure;
        
    }
    
    
    /**
     * Generate minimal values when entities are missing.
     * 
     * @param entitiesByEntityType
     *            the list of entities.
     * @return the kpi result after modification
     */
    public KpiResult inferResults(final List<IEntity> entitiesByEntityType, final Double _minValue) {
    
    
        final KpiResult kpiResult = new KpiResult(new HashMap<EntityKey, Number>(map));
        if (hasFailed()) {
            kpiResult.hasFailed(reasonOfFailure);
            return kpiResult;
        }
        LOGGER.info("From {}", kpiResult.map);
        for (final IEntity entity : entitiesByEntityType) {
            final EntityKey entityKey = entity.getEntityKey();
            if (!kpiResult.map.containsKey(entityKey)) {
                kpiResult.put(entityKey, _minValue);
            }
        }
        LOGGER.info("To {}", kpiResult.map);
        return kpiResult;
    }
    
    
    /**
     * Insert a single result into the map.
     * 
     * @param _key
     * @param _value
     */
    public void insertResult(final EntityKey _key, final double _value) {
    
    
        map.put(_key, _value);
        
    }
    
    
    /**
     * Tests if the result of the kpi is empty
     * 
     * @return
     */
    public boolean isEmpty() {
    
    
        return map.isEmpty();
    }
    
    
    /**
     * Puts a value into the map.
     * 
     * @param _entityKey
     *            the entity key
     * @param _value
     *            the value;
     */
    public void put(final EntityKey _entityKey, final Number _value) {
    
    
        map.put(_entityKey, _value);
        
    }
    
    
    /**
     * Returns the size of the results stored
     */
    public int size() {
    
    
        return map.size();
    }
    
    
}
