/**
 * 
 */

package org.komea.product.backend.api;



import org.komea.eventory.api.engine.ICEPQueryImplementation;



/**
 * This interface the data requested by the CEP engine to instantiate a new CEP Query.
 * 
 * @author sleroy
 */
public interface IQueryDefinition
{
    
    
    /**
     * Returns the query definition
     * 
     * @return the query definition.
     */
    ICEPQueryImplementation getImplementation();
    
    
    /**
     * Returns the name of the query
     * 
     * @return the name of the query.
     */
    String getQueryName();
}
