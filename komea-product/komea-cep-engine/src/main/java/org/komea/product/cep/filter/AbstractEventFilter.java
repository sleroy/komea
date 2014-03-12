/**
 * 
 */

package org.komea.product.cep.filter;



import java.io.Serializable;

import org.komea.product.cep.api.IEventFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This class defines an chain of responsibility pattern over event filter.
 * 
 * @author sleroy
 */
public abstract class AbstractEventFilter implements IEventFilter
{
    
    
    private static final Logger        LOGGER         = LoggerFactory
                                                              .getLogger(AbstractEventFilter.class);
    
    
    private IEventFilter<Serializable> abstractFilter = new NoEventFilter();
    
    
    
    protected AbstractEventFilter(final IEventFilter<Serializable> _filter) {
    
    
        super();
        abstractFilter = _filter;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.IEventFilter#isFiltered(java.lang.Object)
     */
    @Override
    public boolean isFiltered(final Serializable _event) {
    
    
        LOGGER.trace("{} - Test if the event {} is filtered : ", getClass().getName(), _event);
        if (abstractFilter != null && !abstractFilter.isFiltered(_event)) { return false; }
        final boolean eventFiltered = isEventFiltered(_event);
        LOGGER.trace("{} - Test if the event {} is filtered : returns {} ", getClass().getName(),
                _event, eventFiltered);
        return eventFiltered;
    }
    
    
    /**
     * Test internally if the event is filtered.
     * 
     * @param _event
     *            the vent
     * @return true if the event is filtered.
     */
    protected abstract boolean isEventFiltered(final Serializable _event);
    
}
