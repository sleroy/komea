/**
 * 
 */

package org.komea.eventory.formula.tuple;



import java.util.HashMap;
import java.util.Map;

import org.komea.eventory.api.formula.IResultMap;



/**
 * @author sleroy
 * @param <TRes>
 */
public class HashMapResultMap<TKey, TRes> implements IResultMap<TKey, TRes>

{
    
    
    protected final Map<TKey, TRes> resultMap = new HashMap<TKey, TRes>();
    
    
    
    /**
     * 
     */
    public HashMapResultMap() {
    
    
        super();
    }
    
    
    @Override
    public TRes get(final Object _key) {
    
    
        return resultMap.get(_key);
    }
    
    
    @Override
    public Map<TKey, TRes> getTable() {
    
    
        return this.resultMap;
    }
    
    
    @Override
    public TRes getValue(final TKey _valueInTuple) {
    
    
        return resultMap.get(_valueInTuple);
    }
    
    
    @Override
    public void insertEntry(final TKey _key, final TRes _result) {
    
    
        resultMap.put(_key, _result);
        
    }
    
}
