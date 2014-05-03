/**
 * 
 */

package org.komea.product.plugins.kpi.tuplecreator;



import org.komea.product.cep.api.formula.tuple.ITupleCreator;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.enums.EntityType;
import org.komea.product.service.dto.EntityKey;



/**
 * @author sleroy
 */
public class DepartmentTupleCreator implements ITupleCreator<IEvent, EntityKey>
{
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.ITupleCreator#create(java.io.Serializable)
     */
    @Override
    public EntityKey create(final IEvent _event) {
    
    
        return _event.getPersonGroup() == null ? EntityKey.of(EntityType.DEPARTMENT) : _event
                .getPersonGroup().getEntityKey();
    }
    
}
