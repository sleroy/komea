
package org.komea.product.cep.api.tester;



import java.io.Serializable;

import org.komea.eventory.api.engine.ICEPQuery;
import org.komea.product.database.dto.KpiResult;



/**
 */
public interface ICEPQueryTestPredicate<T extends Serializable>
{
    
    
    /**
     * Method evaluate.
     * 
     * @param _epStatement
     *            EPStatement
     */
    void evaluate(final ICEPQuery<T, KpiResult> _epStatement);
}
