/**
 * 
 */

package org.komea.eventory.formula;



import java.io.Serializable;
import java.util.Map;

import org.hamcrest.Matcher;
import org.komea.eventory.api.engine.ICEPStatement;
import org.komea.eventory.api.filters.IEventFilter;
import org.komea.eventory.api.formula.ICEPFormula;
import org.komea.eventory.filter.LambdaJEventFilter;

import ch.lambdaj.collection.LambdaCollections;



/**
 * This class count items previously filter with an event filter.
 * 
 * @author sleroy
 */
public class CountWithFilterFormula<T extends Serializable> implements ICEPFormula<T, Integer>
{
    
    
    private final IEventFilter<T> eventFilter;
    private final Matcher<T>      matcher;
    
    
    
    public CountWithFilterFormula(final IEventFilter<T> _eventFilter) {
    
    
        super();
        eventFilter = _eventFilter;
        matcher = new LambdaJEventFilter<T>(_eventFilter);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPFormula#compute(org.komea.eventory.api.ICEPStatement, java.util.Map)
     */
    @Override
    public Integer compute(final ICEPStatement<T> _statement, final Map<String, Object> _parameters) {
    
    
        return LambdaCollections.with(_statement.getDefaultStorage()).retain(matcher).size();
    }
    
    
}
