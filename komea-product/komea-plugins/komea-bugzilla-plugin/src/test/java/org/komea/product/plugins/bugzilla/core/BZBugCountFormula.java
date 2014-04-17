/**
 * 
 */

package org.komea.product.plugins.bugzilla.core;



import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.komea.eventory.api.engine.ICEPStatement;
import org.komea.eventory.api.formula.ICEPFormula;
import org.komea.eventory.api.formula.ICEPResult;

import com.j2bugzilla.base.Flag;



/**
 * This class defines a formula that counts bugilla bug.
 * 
 * @author sleroy
 */
public class BZBugCountFormula implements ICEPFormula
{
    
    
    private final Set<Flag>           flags      = new HashSet<Flag>();
    
    private final Map<String, Object> parameters = new HashMap<String, Object>();
    
    
    
    /**
     * Adds a flag
     * 
     * @param _flag
     *            the flag to add
     */
    public void addFlag(final Flag _flag) {
    
    
        flags.add(_flag);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.formula.ICEPFormula#compute(org.komea.eventory.api.engine.ICEPStatement, java.util.Map)
     */
    @Override
    public ICEPResult compute(final ICEPStatement _arg0, final Map _arg1) {
    
    
        return null;
    }
    
    
    /**
     * Set a new parameter
     * 
     * @param _key
     *            the key
     * @param _value
     *            the value
     */
    public void setParameter(final String _key, final Object _value) {
    
    
        parameters.put(_key, _value);
        
    }
}
