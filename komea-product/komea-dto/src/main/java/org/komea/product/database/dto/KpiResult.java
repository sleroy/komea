/**
 * 
 */

package org.komea.product.database.dto;



import java.util.Map;

import org.komea.product.service.dto.EntityKey;

import com.google.common.collect.Maps;



/**
 * @author sleroy
 */
public class KpiResult
{
    
    
    private Map<EntityKey, Number> map = Maps.newHashMap();
    
    
    
    public KpiResult() {
    
    
        super();
    }
    
    
    public KpiResult(final Map<EntityKey, Number> _map) {
    
    
        super();
        map = _map;
        
    }
    
    
    public Map<EntityKey, Number> getMap() {
    
    
        return map;
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
    
}
