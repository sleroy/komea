/**
 * 
 */

package org.komea.eventory.api.formula.tuple;



import java.util.Map;

import org.komea.eventory.api.formula.ITupleResultMap;



/**
 * This interface defines the component that process formulars on eventTupler.
 * 
 * @param <R>
 *            the type of result produced by the formula
 * @author sleroy
 */
public interface ITuplerFormula<Result>
{
    
    
    /**
     * Returns the results
     * 
     * @param _ownParameters
     *            the parameters for the formula
     * @param _tupleMap
     *            the tuple map.
     * @return the results
     */
    ITupleResultMap<Result> processMap(IEventTable _tupleMap, Map<String, Object> _ownParameters);
    
}
