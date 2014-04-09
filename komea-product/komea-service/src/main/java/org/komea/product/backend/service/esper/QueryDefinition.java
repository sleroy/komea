/**
 * 
 */

package org.komea.product.backend.service.esper;



import org.komea.product.backend.api.IQueryDefinition;
import org.komea.product.cep.api.ICEPQueryImplementation;



/**
 * This class defines the required data to build a new cep query.
 * 
 * @author sleroy
 */
public class QueryDefinition implements IQueryDefinition
{
    
    
    private ICEPQueryImplementation implementation;
    private String                  queryName;
    
    
    
    /**
     *  
     */
    public QueryDefinition() {
    
    
        super();
    }
    
    
    public QueryDefinition(final String _queryName, final ICEPQueryImplementation _implementation) {
    
    
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
