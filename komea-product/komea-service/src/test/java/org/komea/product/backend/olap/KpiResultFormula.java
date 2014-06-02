/**
 * 
 */

package org.komea.product.backend.olap;



import java.io.Serializable;

import org.komea.eventory.api.engine.ICEPStatement;
import org.komea.eventory.api.formula.ICEPFormula;
import org.komea.product.database.dto.KpiResult;



/**
 * @author sleroy
 */
public class KpiResultFormula implements ICEPFormula<Serializable, KpiResult>
{
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.formula.ICEPFormula#compute(org.komea.eventory.api.engine.ICEPStatement)
     */
    @Override
    public KpiResult compute(final ICEPStatement<Serializable> _arg0) {
    
    
        return new KpiResult();
    }
    
}
