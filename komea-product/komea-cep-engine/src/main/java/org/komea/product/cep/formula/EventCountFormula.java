/**
 * 
 */

package org.komea.product.cep.formula;



import org.komea.product.cep.api.formula.IEventGroup;
import org.komea.product.cep.api.formula.IEventGroupFormula;



/**
 * This class defines a simple count formula on a tuple map.
 * 
 * @author sleroy
 */
public class EventCountFormula implements IEventGroupFormula
{
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.kpi.formula.IEventGroupFormula#evaluate(org.komea.product.cep.api.formula.tuple.IEventGroup)
     */
    @Override
    public Number evaluate(final IEventGroup _eventGroup) {
    
    
        return _eventGroup.getEvents().size();
    }
}
