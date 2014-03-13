/**
 * 
 */

package org.komea.product.cep.formula.tuple;



import java.util.List;

import org.komea.product.cep.api.formula.tuple.ITuple;



/**
 * Builds a new tuple
 * 
 * @author sleroy
 */
public class TupleFactory
{
    
    
    public static ITuple newTuple(final List<Object> _values) {
    
    
        return new ArrayListTuple(_values);
        
    }
    
    
    public static ITuple newTuple(final Object _value, final Object... _values) {
    
    
        return new ArrayListTuple(_value, _values);
    }
}
