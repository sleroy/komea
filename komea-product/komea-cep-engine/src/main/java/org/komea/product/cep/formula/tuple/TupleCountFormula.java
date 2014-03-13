/**
 * 
 */

package org.komea.product.cep.formula.tuple;



import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;
import org.komea.product.cep.api.ITupleResultMap;
import org.komea.product.cep.api.formula.tuple.ITuplerFormula;
import org.komea.product.cep.api.formula.tuple.IEventGroup;
import org.komea.product.cep.api.formula.tuple.ITuple;
import org.komea.product.cep.api.formula.tuple.ITupleMap;



/**
 * This class defines a simple count formula on a tuple map.
 * 
 * @author sleroy
 */
public class TupleCountFormula implements ITuplerFormula<Integer>
{
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.ITuplerFormula#processMap(org.komea.product.cep.api.formula.tuple.ITupleMap,
     * java.util.Map)
     */
    @Override
    public ITupleResultMap<Integer> processMap(
            final ITupleMap _tupleMap,
            final Map<String, Object> _ownParameters) {
    
    
        Validate.notNull(_tupleMap);
        Validate.notNull(_ownParameters);
        final ITupleResultMap<Integer> resultMap = new TupleResultMap<Integer>();
        for (final Entry<ITuple, IEventGroup> entry : _tupleMap.iterator()) {
            resultMap.insertEntry(entry.getKey(), entry.getValue().getEvents().size());
        }
        return resultMap;
    }
}
