/**
 * 
 */

package org.komea.product.plugins.kpi.standard.jenkins.formula;



import java.util.Set;

import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.backend.utils.Treatment;
import org.komea.product.cep.api.formula.IEventGroup;
import org.komea.product.cep.api.formula.IEventGroupFormula;
import org.komea.product.database.alert.IEvent;

import com.google.common.collect.Sets;



/**
 * This class defines the health formula. (number of success build / number of total builds)
 * 
 * @author sleroy
 */
public class ProjectBuildHealthFormula implements IEventGroupFormula<IEvent>
{
    
    
    public static class HealthInformations implements Treatment<IEvent>
    {
        
        
        public static final Set<String> negativeActionsSet = Sets.newHashSet("build_failed",
                                                                   "build_broken",
                                                                   "build_unstable",
                                                                   "build_interrupted");
        
        
        public static Set<String>       positiveActionsSet = Sets.newHashSet("build_started",
                                                                   "build_fixed", "build_complete");
        
        private int                     negativeActions    = 0;
        
        private int                     positiveActions    = 0;
        
        
        
        /*
         * (non-Javadoc)
         * @see org.komea.product.backend.utils.Treatment#apply(java.lang.Object)
         */
        @Override
        public void apply(final IEvent _value) {
        
        
            final String eventKey = _value.getEventType().getEventKey();
            if (positiveActionsSet.contains(eventKey)) {
                positiveActions++;
            } else if (negativeActionsSet.contains(eventKey)) {
                negativeActions++;
            }
            
        }
        
        
        /**
         * Compute the formula.
         * 
         * @return the formula
         */
        public double computePercentageOfSuccessBuild() {
        
        
            return positiveActions - negativeActions;
        }
    }
    
    
    
    /**
     * 
     */
    public ProjectBuildHealthFormula() {
    
    
        super();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.kpi.formula.IEventGroupFormula#evaluate(org.komea.product.cep.api.formula.tuple.IEventGroup)
     */
    @Override
    public Number evaluate(final IEventGroup _eventGroup) {
    
    
        final HealthInformations healthComputation = new HealthInformations();
        CollectionUtil.iterate(_eventGroup.getEvents(), healthComputation);
        return healthComputation.computePercentageOfSuccessBuild();
    }
    
}
