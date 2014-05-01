/**
 * 
 */

package org.komea.eventory.formula;



import java.io.Serializable;
import java.util.Map;

import org.komea.eventory.api.engine.ICEPStatement;
import org.komea.eventory.api.formula.ICEPFormula;



/**
 * @author sleroy
 */
public class CountFormula<T extends Serializable> implements ICEPFormula<T, Integer>
{
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPFormula#compute(org.komea.eventory.api.ICEPStatement, java.util.Map)
     */
    @Override
    public Integer compute(final ICEPStatement<T> _statement, final Map<String, Object> _parameters) {
    
    
        return _statement.getDefaultStorage().size();
    }
    
    
}
