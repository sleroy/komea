/**
 * 
 */

package org.komea.product.plugins.kpi.standard.jenkins.formula;



import org.apache.commons.lang.Validate;
import org.komea.product.backend.utils.Treatment;
import org.komea.product.database.alert.IEvent;



/**
 * This functions counts the number of successful build rate on total builds.
 * 
 * @author sleroy
 */
public class SuccessfulBuildRateFunction implements Treatment<IEvent>
{
    
    
    private int buildComplete = 0;
    
    private int buildStarted  = 0;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.utils.Treatment#apply(java.lang.Object)
     */
    @Override
    public void apply(final IEvent _value) {
    
    
        Validate.notNull(_value);
        
        if ("build_started".equals(_value.getEventType().getEventKey())) {
            buildStarted++;
        }
        if ("build_complete".equals(_value.getEventType().getEventKey())) {
            buildComplete++;
        }
        
    }
    
    
    /**
     * Computew the build rate.
     * 
     * @return
     */
    public float compute() {
    
    
        if (buildStarted == 0) { return 0.0f; }
        return Math.min(100.0f, buildComplete * 100.0f / buildStarted);
    }
}
