/**
 * 
 */

package org.komea.product.plugins.kpi.filters;



import org.komea.product.cep.api.IEventFilter;
import org.komea.product.database.alert.IEvent;



/**
 * This filter selects only the event with user information.
 * 
 * @author sleroy
 */
public class WithUserFilter implements IEventFilter<IEvent>
{
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.IEventFilter#isFiltered(java.io.Serializable)
     */
    @Override
    public boolean isFiltered(final IEvent _event) {
    
    
        return _event.getPerson() != null;
    }
    
}
