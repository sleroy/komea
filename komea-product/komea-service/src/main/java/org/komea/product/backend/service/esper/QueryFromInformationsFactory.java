/**
 * 
 */

package org.komea.product.backend.service.esper;



import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.product.backend.api.IQueryFromInformationsFactory;
import org.komea.product.backend.service.kpi.FormulaID;
import org.komea.product.cep.api.queries.ICEPQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * @author sleroy
 */
@Service
public class QueryFromInformationsFactory implements IQueryFromInformationsFactory
{
    
    
    @Autowired
    private ICEPQueryFactory cepQueryFactory;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.esper.IQueryFromInformationsFactory#newCEPQuery(java.lang.String,
     * org.komea.eventory.api.engine.ICEPQueryImplementation)
     */
    @Override
    public QueryInformations newCEPQuery(
            final FormulaID _queryName,
            final ICEPQueryImplementation _queryImplementation) {
    
    
        return new QueryInformations(_queryName,
                cepQueryFactory.instantiateQuery(_queryImplementation));
        
    }
}
