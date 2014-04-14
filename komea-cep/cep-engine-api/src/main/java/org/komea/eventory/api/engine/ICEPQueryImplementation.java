/**
 * 
 */

package org.komea.eventory.api.engine;



import java.util.List;
import java.util.Map;

import org.komea.eventory.api.filters.IFilterDefinition;
import org.komea.eventory.api.formula.ICEPFormula;



/**
 * This interface offers the requested methods to define a query in the CEP engine.
 * 
 * @author sleroy
 */
public interface ICEPQueryImplementation
{
    
    
    /**
     * Returns the filter definitions.
     * 
     * @return the filter definitions.
     */
    List<IFilterDefinition> getFilterDefinitions();
    
    
    /**
     * Returns the formula
     * 
     * @return the formula
     */
    ICEPFormula getFormula();
    
    
    /**
     * Returns the parameters of the cep query
     * 
     * @return the parameters of the query.
     */
    Map<String, Object> getParameters();
    
}
