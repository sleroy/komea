/**
 * 
 */

package org.komea.product.backend.groovy;



import org.komea.eventory.cache.guava.GoogleCacheStorage;
import org.komea.product.backend.service.SpringService;
import org.komea.product.cep.backend.cache.CacheStorageFactoryService;
import org.springframework.web.context.support.StaticWebApplicationContext;



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
        final SpringService springService = new SpringService();
        springService.setApplicationContext(new StaticWebApplicationContext());
        groovyEngineService.setSpringService(springService);
        groovyEngineService.init();
        final boolean res = groovyEngineService.isValidFormula(_formula);
        groovyEngineService.destroy();
        return res;
    }
}
