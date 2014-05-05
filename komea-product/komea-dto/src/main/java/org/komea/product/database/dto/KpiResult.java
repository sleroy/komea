/**
 * 
 */

package org.komea.product.database.dto;



import java.util.Collections;
import java.util.Map;

import org.komea.product.service.dto.EntityKey;

import com.google.common.collect.Maps;



/**
 * @author sleroy
 */
public class KpiResult
{
    
    
    public static final KpiResult  EMPTY = new KpiResult()
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
    
    private Map<EntityKey, Number> map   = Maps.newHashMap();
    
    
    
    public KpiResult() {
    
    
        super();
    }
    
    
    public KpiResult(final Map<EntityKey, Number> _map) {
    
    
        super();
        map = _map;
        
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
    
    
    /**
     * Returns the value of an entity.
     * 
     * @param _entityKey
     *            the entity key.
     * @return the value.
     */
    public Number getValue(final EntityKey _entityKey) {
    
    
        Number number = map.get(_entityKey);
        if (number == null) return 0d; 
		return number;
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
