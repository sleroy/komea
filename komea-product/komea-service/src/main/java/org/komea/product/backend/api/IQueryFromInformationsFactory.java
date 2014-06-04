/**
 * 
 */

package org.komea.product.backend.api;



import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.product.backend.service.esper.QueryInformations;



/**
 * @author sleroy
 *
 */
public interface IQueryFromInformationsFactory
{
    
    
    /**
     * Builds a new cep query.
     * 
     * @param _queryImplementation
     *            the query informations.
     * @return a new cep query
     */
    public abstract QueryInformations newCEPQuery(
            String _queryName,
            ICEPQueryImplementation _queryImplementation);
    
}
