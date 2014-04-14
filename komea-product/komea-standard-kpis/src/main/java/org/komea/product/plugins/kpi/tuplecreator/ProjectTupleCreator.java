/**
 * 
 */

package org.komea.product.plugins.kpi.tuplecreator;



import org.komea.eventory.api.formula.tuple.ITuple;
import org.komea.eventory.api.formula.tuple.ITupleCreator;
import org.komea.eventory.formula.tuple.TupleFactory;
import org.komea.product.database.alert.IEvent;



/**
 * @author sleroy
 */
public class ProjectTupleCreator implements ITupleCreator<IEvent>
{
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.ITupleCreator#create(java.io.Serializable)
     */
    @Override
    public ITuple create(final IEvent _event) {
    
    
        return TupleFactory.newTuple(_event.getProject().getEntityKey());
    }
    
}
