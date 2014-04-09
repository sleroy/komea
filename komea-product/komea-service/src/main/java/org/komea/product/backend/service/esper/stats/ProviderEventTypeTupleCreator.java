/**
 * 
 */

package org.komea.product.backend.service.esper.stats;



import org.komea.product.cep.api.formula.tuple.ITuple;
import org.komea.product.cep.api.formula.tuple.ITupleCreator;
import org.komea.product.cep.formula.tuple.TupleFactory;
import org.komea.product.database.alert.IEvent;



/**
 * Creates a tuple from provider and eventType.
 * 
 * @author sleroy
 */
public class ProviderEventTypeTupleCreator implements ITupleCreator<IEvent>
{
    
    
    @Override
    public ITuple create(final IEvent _event) {
    
    
        return TupleFactory.newTuple(_event.getProvider().getName(), _event.getEventType()
                .getEventKey());
    }
}
