/**
 * 
 */

package org.komea.product.backend.api;



import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.service.kpi.FormulaID;



/**
 * This interface the data requested by the CEP engine to instantiate a new CEP
 * Query.
 * 
 * @author sleroy
 */
public interface IQueryInformations
{
    
    
    /**
     * Returns the query definition
     * 
     * @return the query definition.
     */
    IQuery getImplementation();
    
    
    /**
     * Returns the name of the query
     * 
     * @return the name of the query.
     */
    FormulaID getQueryName();
}
