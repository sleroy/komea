/**
 * 
 */

package org.komea.product.backend.service.esper;



import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.api.IQueryInformations;
import org.komea.product.backend.service.kpi.FormulaID;



/**
 * This class defines the required data to build a new cep query.
 * 
 * @author sleroy
 */
public class QueryInformations implements IQueryInformations
{
    
    
    private IQuery    implementation;
    
    
    private FormulaID queryName;
    
    
    
    /**
     *  
     */
    public QueryInformations() {
    
    
        super();
    }
    
    
    /**
     * @param _rawID
     * @param _cepQueryImplementationStub
     */
    public QueryInformations(final FormulaID _rawID, final IQuery _cepQueryImplementationStub) {
    
    
        queryName = _rawID;
        implementation = _cepQueryImplementationStub;
        
    }
    
    
    public QueryInformations(final String _queryName, final IQuery _queryImplementation) {
    
    
        queryName = FormulaID.of(_queryName);
        implementation = _queryImplementation;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.api.IQueryDefinition#getImplementation()
     */
    @Override
    public IQuery getImplementation() {
    
    
        return implementation;
    }
    
    
    @Override
    public FormulaID getQueryName() {
    
    
        return queryName;
    }
    
    
    public void setImplementation(final IQuery _implementation) {
    
    
        implementation = _implementation;
    }
    
    
    public void setQueryName(final FormulaID _queryName) {
    
    
        queryName = _queryName;
    }
    
}
