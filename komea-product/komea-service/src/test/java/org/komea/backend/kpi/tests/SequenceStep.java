/**
 * 
 */

package org.komea.backend.kpi.tests;



import org.komea.product.cep.api.IEventFilter;



/**
 * This class defines a step of sequence of events.
 * 
 * @author sleroy
 */
public class SequenceStep
{
    
    
    private final IEventFilter<?> filter;
    
    
    
    /**
     * @param _filter
     */
    public SequenceStep(final IEventFilter<?> _filter) {
    
    
        filter = _filter;
        
        
    }
    
}
