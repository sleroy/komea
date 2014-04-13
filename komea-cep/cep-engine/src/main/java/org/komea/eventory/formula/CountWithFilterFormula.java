/**
 * 
 */

package org.komea.eventory.formula;



import java.io.Serializable;
import java.util.Map;

import org.hamcrest.Matcher;
import org.komea.eventory.filter.LambdaJEventFilter;
import org.komea.eventory.query.CEPResult;
import org.komea.product.cep.api.ICEPFormula;
import org.komea.product.cep.api.ICEPResult;
import org.komea.product.cep.api.ICEPStatement;
import org.komea.product.cep.api.IEventFilter;

import ch.lambdaj.collection.LambdaCollections;



/**
 * This class count items previously filter with an event filter.
 * 
 * @author sleroy
 */
public class CountWithFilterFormula<T extends Serializable> implements ICEPFormula<T>
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
    public ICEPResult compute(
            final ICEPStatement<T> _statement,
            final Map<String, Object> _parameters) {
    
    
        return CEPResult.buildFromNumber(LambdaCollections.with(_statement.getDefaultStorage())
                .retain(matcher).size());
    }
    
    
}
