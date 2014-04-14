/**
 * 
 */

package org.komea.eventory.api.formula;



import java.util.List;
import java.util.Map;

import org.komea.eventory.api.formula.tuple.ITuple;



/**
 * This interface defines the object returned as a map from CEPQueries.
 * 
 * @author sleroy
 */
public interface ITupleResultMap<TRes>
{
    
    
    /**
     * Converts the result as a hashmap where tuple are converted into a pojo.
     * 
     * @param _pojoClass
     *            the pojo class
     * @return the hash map
     */
    public <T> Map<T, TRes> asPojoMap(String[] _fieldSet, Class<T> _pojoClass);
    
    
    /**
     * Converts the result into a list of pojo, the pojo must contains a field named value.
     * Costs (O(2n) , number of items in the map + n pojo creation/reflection.
     * 
     * @return the list of pojo.
     */
    public <T> List<T> asPojoRows(String[] _fieldSet, Class<T> _rowPojo);
    
    
    /**
     * Converts the result into a simplified map if the tuple contains only one element.
     * Costs(O(n) + n pojo creation/reflection
     * 
     * @return the results into a simplified map.
     */
    public <T> Map<T, TRes> asSimplifiedMap();
    
    
    /**
     * Converts the result into a list of tuples where the result is added a the value property.
     * Costs(n) : number of items in the map + n inserts in an hashmap (amortized)
     * 
     * @return the list of tuples.
     */
    public <T> List<ITuple> asTupleRows();
    
    
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
    public <T> Map<ITuple, TRes> getTable();
    
    
    /**
     * Returns the value.
     * 
     * @param _key
     *            the key.
     * @return the value.
     */
    public TRes getValue(ITuple _key);
    
    
    /**
     * Inserts an entry into the result map
     * 
     * @param _key
     *            the key
     * @param _result
     *            the result.
     */
    public void insertEntry(ITuple _key, TRes _result);
}
