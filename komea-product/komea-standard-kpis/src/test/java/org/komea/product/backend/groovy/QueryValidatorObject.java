/**
 * 
 */

package org.komea.product.backend.groovy;



import org.komea.eventory.api.cache.ICacheStorageFactory;
import org.komea.eventory.cache.guava.GoogleCacheStorage;
import org.komea.product.backend.api.ISpringService;
import org.komea.product.cep.backend.cache.CacheStorageFactoryService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



/**
 * This clas is used for test only.
 * 
 * @author sleroy
 */
public class QueryValidatorObject
{
    
    
    public boolean validateQuery(final String _formula) {
    
    
        final CacheStorageFactoryService cacheStorageFactoryService =
                new CacheStorageFactoryService();
        cacheStorageFactoryService.setImplementation(GoogleCacheStorage.class.getCanonicalName());
        cacheStorageFactoryService.init();
        
        final GroovyEngineService groovyEngineService = new GroovyEngineService();
        final ISpringService mock = mock(ISpringService.class);
        when(mock.getBean(ICacheStorageFactory.class)).thenReturn(cacheStorageFactoryService);
        groovyEngineService.setSpringService(mock);
        groovyEngineService.init();
        final boolean res = groovyEngineService.isValidFormula(_formula);
        groovyEngineService.destroy();
        return res;
    }
}
