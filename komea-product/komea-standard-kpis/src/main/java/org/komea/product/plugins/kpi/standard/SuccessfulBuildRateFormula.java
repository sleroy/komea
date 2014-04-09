/**
 * 
 */

package org.komea.product.plugins.kpi.standard;



import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.cep.api.ITupleResultMap;
import org.komea.product.cep.api.formula.tuple.IEventGroup;
import org.komea.product.cep.api.formula.tuple.IEventTable;
import org.komea.product.cep.api.formula.tuple.ITuple;
import org.komea.product.cep.api.formula.tuple.ITuplerFormula;
import org.komea.product.cep.formula.tuple.TupleResultMap;
import org.komea.product.database.alert.IEvent;



/**
 * This formula computes successful build rate
 * 
 * @author sleroy
 */
public class SuccessfulBuildRateFormula implements ITuplerFormula<Float>
{
    
    
    public SuccessfulBuildRateFormula() {
    
    
        super();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.ITuplerFormula#processMap(org.komea.product.cep.api.formula.tuple.IEventTable,
     * java.util.Map)
     */
    @Override
    public ITupleResultMap<Float> processMap(final IEventTable _tupleMap, final Map _ownParameters) {
    
    
        final TupleResultMap<Float> tupleResultMap = new TupleResultMap<Float>();
        for (final Entry<ITuple, IEventGroup> entry : _tupleMap.iterator()) {
            final IEventGroup value = entry.getValue();
            final SuccessfulBuildRateFunction rateFunction = new SuccessfulBuildRateFunction();
            final List<IEvent> events = value.getEvents();
            CollectionUtil.iterate(events, rateFunction);
            final Float rate = rateFunction.compute();
            tupleResultMap.insertEntry(entry.getKey(), rate);
        }
        return tupleResultMap;
    }
}
