/**
 * 
 */

package org.komea.product.backend.service.kpi;



import java.util.Collections;

import org.komea.product.cep.api.formula.tuple.ITuple;
import org.komea.product.cep.api.formula.tuple.ITupleCreator;
import org.komea.product.cep.formula.tuple.ArrayListTuple;
import org.komea.product.database.alert.IEvent;



/**
 * This class defines a group function where project are sorted.
 * 
 * @author sleroy
 */
public class ProjectEntityKeyTupleCreator implements ITupleCreator<IEvent>
{
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.ITupleCreator#create(java.io.Serializable)
     */
    @Override
    public ITuple create(final IEvent _event) {
    
    
        return new ArrayListTuple(Collections.singletonList(_event.getProject().getEntityKey()));
    }
}
