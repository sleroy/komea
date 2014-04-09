/**
 * 
 */

package org.komea.product.backend.utils;



import java.util.Collections;
import java.util.HashMap;
import java.util.Map;



/**
 * @author sleroy
 */
public class MapPopulation<K, V>
{
    
    
    public static <K1, V1> MapPopulation<K1, V1> create() {
    
    
        return new MapPopulation<K1, V1>();
        
    }
    
    
    
    private final Map<K, V> expectedMap = new HashMap<K, V>();
    
    
    
    /**
     * @param _key
     * @param _value
     * @return
     */
    public MapPopulation<K, V> addEntry(final K _key, final V _value) {
    
    
        expectedMap.put(_key, _value);
        
        return this;
    }
    
    
    public Map<K, V> build() {
    
    
        return Collections.unmodifiableMap(expectedMap);
    }
}
