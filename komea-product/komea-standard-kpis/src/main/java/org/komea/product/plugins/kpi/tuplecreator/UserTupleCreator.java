/**
 * 
 */

package org.komea.product.plugins.kpi.tuplecreator;



import org.komea.product.cep.api.formula.tuple.ITuple;
import org.komea.product.cep.api.formula.tuple.ITupleCreator;
import org.komea.product.cep.formula.tuple.TupleFactory;
import org.komea.product.database.alert.IEvent;



/**
 * @author sleroy
 */
public class UserTupleCreator implements ITupleCreator<IEvent>
{
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.ITupleCreator#create(java.io.Serializable)
     */
    @Override
    public ITuple create(final IEvent _event) {
    
    
        return TupleFactory.newTuple(_event.getPerson().getEntityKey());
    }
    
}
