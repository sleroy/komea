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
 * @author sleroy
 */
public class CountFormula<T extends Serializable> implements ICEPFormula<T>
{
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPFormula#compute(org.komea.product.cep.api.ICEPStatement, java.util.Map)
     */
    @Override
    public ICEPResult compute(
            final ICEPStatement<T> _statement,
            final Map<String, Object> _parameters) {
    
    
        return CEPResult.buildFromNumber(_statement.getDefaultStorage().size());
    }
    
    
}
