/**
 * 
 */

package org.komea.product.cep.api.formula;



import java.util.Map;



/**
 * This interface defines the object returned as a map from CEPQueries.
 * 
 * @author sleroy
 */
public interface IResultMap<TKey, TRes>
{
    
    
    /**
     * Returns the value.
     * 
     * @param _key
     *            the key.
     * @return the value.
     */
    public TRes get(Object _key);
    
    
    /**
     * Converts the result into a tuple map.
     * Costs(O(1))
     * 
     * @return the results into a tuple map.
     */
    public Map<TKey, TRes> getTable();
    
    
    /**
     * Returns the value.
     * 
     * @param _key
     *            the key.
     * @return the value.
     */
    public TRes getValue(TKey _key);
    
    
    /**
     * Inserts an entry into the result map
     * 
     * @param _key
     *            the key
     * @param _result
     *            the result.
     */
    public void insertEntry(TKey _key, TRes _result);
    
    
}
