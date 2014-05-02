/**
 * 
 */

package org.komea.eventory.filter;



import java.io.Serializable;
import java.util.Map;

import org.komea.eventory.api.filters.IEventFilter;
import org.komea.eventory.formula.ElFormula;



/**
 * This class defines a filter of events based on Spring EL Language.
 * 
 * @author sleroy
 */
public class ElEventFilter<T extends Serializable> implements IEventFilter<T>
{
    
    
    private final ElFormula<Boolean> formula;
    private Map<String, Object>      parameters;
    
    
    
    /**
     * Formula filter.
     * 
     * @param _filter
     */
    public ElEventFilter(final String _formula) {
    
    
        formula = new ElFormula<Boolean>(_formula, Boolean.class);
    }
    
    
    /**
     * Formula filter.
     * 
     * @param _filterw
     */
    public ElEventFilter(final String _formula, final Map<String, Object> _parameters) {
    
    
        parameters = _parameters;
        formula = new ElFormula<Boolean>(_formula, Boolean.class);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.filter.AbstractEventFilter#isEventFiltered(java.io.Serializable)
     */
    @Override
    public boolean isFiltered(final T _event) {
    
    
        return formula.getValue(_event, parameters);
    }
}
