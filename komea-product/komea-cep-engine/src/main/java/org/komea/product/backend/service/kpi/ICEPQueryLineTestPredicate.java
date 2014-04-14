
package org.komea.product.backend.service.kpi;



import org.komea.eventory.api.formula.tuple.ITuple;



/**
 */
public interface ICEPQueryLineTestPredicate
{
    
    
    /**
     * Method evaluate.
     * 
     * @param _value
     *            Map<String,Object>
     */
    void evaluate(final ITuple _value);
}
