/**
 * 
 */

package org.komea.eventory.formula;



import java.io.Serializable;
import java.util.Map;

import org.komea.eventory.api.engine.ICEPStatement;
import org.komea.eventory.api.formula.ICEPFormula;
import org.komea.eventory.api.formula.ICEPResult;
import org.komea.eventory.query.CEPResult;



/**
 * This class defines the implemrntation of a formula that does nothing
 * 
 * @author sleroy
 */
public class NoCEPFormula implements ICEPFormula<Serializable>
{
    
    
    /**
     * 
     */
    public NoCEPFormula() {
    
    
        super();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPFormula#compute(org.komea.eventory.api.ICEPStatement, java.util.Map)
     */
    @Override
    public ICEPResult compute(
            final ICEPStatement<Serializable> _statement,
            final Map<String, Object> _parameters) {
    
    
        return CEPResult.buildFromCustomType(new Object());
    }
    
    
}
