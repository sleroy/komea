/**
 * 
 */

package org.komea.product.cep.api.queries;



import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.eventory.query.CEPQuery;



/**
 * @author sleroy
 *
 */
public interface ICEPQueryFactory
{
    
    
    /**
     * Instantiate a cep query
     * 
     * @param _formulaRawID
     * @param _queryImplementation
     * @return
     */
    public abstract CEPQuery instantiateQuery(ICEPQueryImplementation _queryImplementation);
    
}
