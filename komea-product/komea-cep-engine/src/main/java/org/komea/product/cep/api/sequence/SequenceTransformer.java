/**
 * 
 */

package org.komea.product.cep.api.sequence;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.komea.backend.kpi.tests.SequenceStep;
import org.komea.product.cep.api.IEventFilter;
import org.komea.product.cep.api.IEventTransformer;
import org.komea.product.cep.api.TransformedEvent;

import com.google.common.collect.Lists;



/**
 * @author sleroy
 */
public class SequenceTransformer implements IEventTransformer
{
    
    
    /**
     * Builds a transformer based on a sequence of events.
     * 
     * @param _eventFilter
     * @param _eventFilter
     * @return
     */
    public static IEventTransformer build(
            final IEventFilter _eventFilter,
            final IEventFilter... _eventFilters) {
    
    
        return new SequenceTransformer(Lists.asList(_eventFilter, _eventFilters));
    }
    
    
    
    private final SequenceStepEvaluator sequenceStepEvaluator;
    
    
    
    /**
     * Build a sequence transformer.
     * 
     * @param _eventFilters
     *            the event filter.
     */
    @SuppressWarnings("rawtypes")
    public SequenceTransformer(final List<IEventFilter> _eventFilters) {
    
    
        final List<SequenceStep> sequenceSteps = new ArrayList();
        for (final IEventFilter filter : _eventFilters) {
            sequenceSteps.add(new SequenceStep(filter));
        }
        sequenceStepEvaluator = new SequenceStepEvaluator(sequenceSteps);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.IEventTransformer#transform(java.io.Serializable)
     */
    @Override
    public TransformedEvent transform(final Serializable _event) {
    
    
        if (sequenceStepEvaluator.evaluate(_event)) { return sequenceStepEvaluator
                .extractSequence(); }
        return null;
    }
    
}
