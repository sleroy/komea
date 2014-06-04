/**
 * 
 */

package org.komea.product.cep.backend.cache;



import org.komea.eventory.api.cache.ICacheStorageFactory;
import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.eventory.query.CEPQuery;
import org.komea.product.cep.api.queries.ICEPQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * This service is a factory to initialized CEP Queries
 * 
 * @author sleroy
 */
@Service
public class CEPQueryFactory implements ICEPQueryFactory
{
    
    
    @Autowired
    private ICacheStorageFactory cacheStorageFactory;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.backend.cache.ICEPQueryFactory#instantiateQuery(org.komea.eventory.api.engine.ICEPQueryImplementation)
     */
    @Override
    public CEPQuery instantiateQuery(final ICEPQueryImplementation _queryImplementation) {
    
    
        return new CEPQuery(_queryImplementation, cacheStorageFactory);
    }
    
    
    public void setCacheStorageFactory(final ICacheStorageFactory _cacheStorageFactory) {
    
    
        cacheStorageFactory = _cacheStorageFactory;
    }
}
