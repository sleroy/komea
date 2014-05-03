/**
 * 
 */

package org.komea.product.cep.formula.tuple;



import java.util.ArrayList;
import java.util.List;

import org.komea.product.cep.api.formula.tuple.ITuple;



/**
 * Builds a new tuple
 * 
 * @author sleroy
 */
public class TupleFactory
{
    
    
    public static ITuple newTuple(final List<?> _values) {
    
    
        return new ArrayListTuple(_values);
        
    }
    
    
    /**
     * Builds a tuple from a list of values
     * 
     * @param _value
     *            the value
     * @param _values
     *            the list of values
     * @return the tuple
     */
    public static <T> ITuple newTuple(final T _value, final T... _values) {
    
    
        final ArrayList<T> arrayList = new ArrayList<T>();
        arrayList.add(_value);
        for (final T itemT : _values) {
            arrayList.add(itemT);
        }
        
        return new ArrayListTuple(arrayList);
    }
}
