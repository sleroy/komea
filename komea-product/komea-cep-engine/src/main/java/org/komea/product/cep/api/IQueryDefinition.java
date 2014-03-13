/**
 * 
 */

package org.komea.product.cep.api;



import java.util.List;
import java.util.Map;



/**
 * This interface offers the requested methods to define a query in the CEP engine.
 * 
 * @author sleroy
 */
public interface IQueryDefinition
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
     * Returns the name of the query
     * 
     * @return the name of the query.
     */
    String getName();
    
    
    /**
     * Returns the parameters of the cep query
     * 
     * @return the parameters of the query.
     */
    Map<String, Object> getParameters();
    
}
