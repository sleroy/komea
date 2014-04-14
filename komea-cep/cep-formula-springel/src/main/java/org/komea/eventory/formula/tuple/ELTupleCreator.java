/**
 * 
 */

package org.komea.eventory.formula.tuple;



import java.io.Serializable;

import org.komea.eventory.api.formula.tuple.ITuple;
import org.komea.eventory.api.formula.tuple.ITupleCreator;
import org.komea.eventory.formula.ElFormula;
import org.komea.eventory.formula.tuple.TupleFactory;



/**
 * This class defines an EL Tuple creator;
 * 
 * @author sleroy
 */
public class ELTupleCreator implements ITupleCreator
{
    
    
    private final String formula;
    
    
    
    public ELTupleCreator(final String _formula) {
    
    
        formula = _formula;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.formula.tuple.ITupleCreator#create(java.io.Serializable)
     */
    @Override
    public ITuple create(final Serializable _event) {
    
    
        return TupleFactory.newTuple(new ElFormula(formula, Object.class).getValue(_event));
    }
    
}
