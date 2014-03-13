/**
 * 
 */

package org.komea.product.cep.formula.tuple;



import java.util.Map;

import org.komea.product.cep.api.formula.tuple.ITuple;



/**
 * Builds a new tuple
 * 
 * @author sleroy
 */
public class TupleFactory
{
    
    
    public static ITuple newTuple(final Map<String, Object> _propertiesMap) {
    
    
        return new ArrayListTuple(_propertiesMap);
        
    }
    
    
    public static ITuple newTuple(final String[] _strings, final Object... _values) {
    
    
        return new ArrayListTuple(_strings, _values);
    }
}
