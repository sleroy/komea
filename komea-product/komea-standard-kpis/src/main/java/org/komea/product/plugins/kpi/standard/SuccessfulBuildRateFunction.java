/**
 * 
 */

package org.komea.product.plugins.kpi.standard;



import org.komea.product.backend.utils.Treatment;
import org.komea.product.database.alert.IEvent;



/**
 * This functions counts the nul
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
        return buildStarted * 100.0f / buildComplete;
    }
}
