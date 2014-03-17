/**
 * 
 */

package org.komea.product.plugins.kpi.standard;



import org.komea.product.cep.api.IEventFilter;
import org.komea.product.database.alert.IEvent;



/**
 * This class defines the filter that allows only filter with projects.
 * 
 * @author sleroy
 */
public class WithProjectFilter implements IEventFilter<IEvent>
{
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.IEventFilter#isFiltered(java.io.Serializable)
     */
    @Override
    public boolean isFiltered(final IEvent _event) {
    
    
        return _event.getProject() != null;
    }
    
}
