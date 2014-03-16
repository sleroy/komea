/**
 * 
 */

package org.komea.product.cep.formula.tuple;



import java.util.List;

import org.komea.product.cep.api.formula.tuple.ITuple;

import com.google.common.collect.Lists;



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
    
    
    public static <T> ITuple newTuple(final T _value, final T... _values) {
    
    
        return new ArrayListTuple(Lists.asList(_value, _values));
    }
}
