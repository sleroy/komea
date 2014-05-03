/**
 * 
 */

package org.komea.product.backend.service.esper;



import java.io.Serializable;
import java.util.Map;

import org.komea.eventory.api.engine.ICEPStatement;
import org.komea.eventory.api.formula.ICEPFormula;
import org.komea.product.database.dto.KpiResult;



/**
 * @author sleroy
 */
public class NoKpiResultFormula implements ICEPFormula<Serializable, KpiResult>
{
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.formula.ICEPFormula#compute(org.komea.eventory.api.engine.ICEPStatement, java.util.Map)
     */
    @Override
    public KpiResult compute(final ICEPStatement _arg0, final Map _arg1) {
    
    
        return new KpiResult();
    }
    
}
