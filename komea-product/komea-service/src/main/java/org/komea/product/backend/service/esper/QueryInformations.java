/**
 * 
 */

package org.komea.product.backend.service.esper;



import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.product.backend.api.IQueryInformations;



/**
 * This class defines the required data to build a new cep query.
 * 
 * @author sleroy
 */
public class QueryInformations implements IQueryInformations
{
    
    
    private ICEPQueryImplementation implementation;
    private String                  queryName;
    
    
    
    /**
     *  
     */
    public QueryInformations() {
    
    
        super();
    }
    
    
    public QueryInformations(final String _queryName, final ICEPQueryImplementation _implementation) {
    
    
        queryName = _queryName;
        implementation = _implementation;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.api.IQueryDefinition#getImplementation()
     */
    @Override
    public ICEPQueryImplementation getImplementation() {
    
    
        return implementation;
    }
    
    
    @Override
    public String getQueryName() {
    
    
        return queryName;
    }
    
    
    public void setImplementation(final ICEPQueryImplementation _implementation) {
    
    
        implementation = _implementation;
    }
    
    
    public void setQueryName(final String _queryName) {
    
    
        queryName = _queryName;
    }
    
}
