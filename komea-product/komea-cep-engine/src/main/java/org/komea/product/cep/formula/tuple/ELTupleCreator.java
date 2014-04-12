/**
 * 
 */

package org.komea.product.cep.formula.tuple;



import java.io.Serializable;

import org.komea.product.cep.api.formula.tuple.ITuple;
import org.komea.product.cep.api.formula.tuple.ITupleCreator;
import org.komea.product.cep.formula.ElFormula;



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
     * @see org.komea.product.cep.api.formula.tuple.ITupleCreator#create(java.io.Serializable)
     */
    @Override
    public ITuple create(final Serializable _event) {
    
    
        return TupleFactory.newTuple(new ElFormula(formula, Object.class).getValue(_event));
    }
    
}
