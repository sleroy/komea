/**
 * 
 */

package org.komea.product.plugins.kpi.standard;



import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.backend.utils.Treatment;
import org.komea.product.cep.api.ITupleResultMap;
import org.komea.product.cep.api.formula.tuple.IEventGroup;
import org.komea.product.cep.api.formula.tuple.IEventTable;
import org.komea.product.cep.api.formula.tuple.ITuple;
import org.komea.product.cep.api.formula.tuple.ITuplerFormula;
import org.komea.product.cep.formula.tuple.TupleResultMap;
import org.komea.product.database.alert.IEvent;



/**
 * This class defines the health formula.
 * 
 * @author sleroy
 */
public class HealthFormula implements ITuplerFormula
{
    
    
    public static class HealthInformations implements Treatment<IEvent>
    {
        
        
        private int negativeActions = 0;
        
        
        private int positiveActions = 0;
        
        
        
        /*
         * (non-Javadoc)
         * @see org.komea.product.backend.utils.Treatment#apply(java.lang.Object)
         */
        @Override
        public void apply(final IEvent _value) {
        
        
            if ("build_fixed".equals(_value.getEventType().getEventKey())) {
                positiveActions++;
            } else if ("build_broken".equals(_value.getEventType().getEventKey())) {
                negativeActions++;
            }
            
        }
        
        
        /**
         * Compute the formula.
         * 
         * @return the formula
         */
        public double compute() {
        
        
            if (positiveActions + negativeActions == 0) { return 0; }
            
            return Math.min(100.0d, 100.0d * positiveActions / (positiveActions + negativeActions));
        }
    }
    
    
    
    /**
     * 
     */
    public HealthFormula() {
    
    
        super();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.ITuplerFormula#processMap(org.komea.product.cep.api.formula.tuple.IEventTable,
     * java.util.Map)
     */
    @Override
    public ITupleResultMap processMap(final IEventTable _tupleMap, final Map _ownParameters) {
    
    
        final TupleResultMap<Double> tupleResultMap = new TupleResultMap<Double>();
        for (final Entry<ITuple, IEventGroup> entry : _tupleMap.iterator()) {
            final IEventGroup value = entry.getValue();
            final List<IEvent> events = value.getEvents();
            final HealthInformations healthComputation = new HealthInformations();
            CollectionUtil.iterate(events, healthComputation);
            final double rate = healthComputation.compute();
            tupleResultMap.insertEntry(entry.getKey(), rate);
        }
        return tupleResultMap;
    }
    
}
