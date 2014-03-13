/**
 * 
 */

package org.komea.product.cep.formula;



import java.io.Serializable;
import java.util.Map;

import org.komea.product.cep.api.ICEPFormula;
import org.komea.product.cep.api.ICEPResult;
import org.komea.product.cep.api.ICEPStatement;
import org.komea.product.cep.query.CEPResult;



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
     * @see org.komea.product.cep.api.ICEPFormula#compute(org.komea.product.cep.api.ICEPStatement, java.util.Map)
     */
    @Override
    public ICEPResult compute(
            final ICEPStatement<Serializable> _statement,
            final Map<String, Object> _parameters) {
    
    
        return CEPResult.buildFromCustomType(new Object());
    }
    
    
}
