/**
 * 
 */

package org.komea.product.plugins.kpi.standard.jenkins.formula;



import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.cep.api.formula.IEventGroup;
import org.komea.product.cep.api.formula.IEventGroupFormula;
import org.komea.product.database.alert.IEvent;



/**
 * This formula computes the percentage of successful build for Jenkins. It is computed per project.
 * 
 * @author sleroy
 */
public class SuccessfulBuildRateFormula implements IEventGroupFormula<IEvent>
{
    
    
    public SuccessfulBuildRateFormula() {
    
    
        super();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.kpi.formula.IEventGroupFormula#evaluate(org.komea.product.cep.api.formula.tuple.IEventGroup)
     */
    @Override
    public Number evaluate(final IEventGroup<IEvent> _eventGroup) {
    
    
        final SuccessfulBuildRateFunction rateFunction = new SuccessfulBuildRateFunction();
        CollectionUtil.iterate(_eventGroup.getEvents(), rateFunction);
        return rateFunction.compute();
    }
}
