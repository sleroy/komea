/**
 * 
 */

package org.komea.product.backend.groovy;



import org.komea.eventory.cache.guava.GoogleCacheStorage;
import org.komea.eventory.utils.PluginUtils;
import org.komea.product.backend.service.SpringService;
import org.komea.product.cep.backend.cache.CacheFactory;
import org.springframework.web.context.support.StaticWebApplicationContext;



/**
 * This clas is used for test only.
 * 
 * @author sleroy
 */
public class QueryValidatorObject
{
    
    
    public boolean validateQuery(final String _formula) {
    
    
        final CacheFactory cacheFactory = new CacheFactory();
        cacheFactory.setImplementation(GoogleCacheStorage.class.getCanonicalName());
        cacheFactory.init();
        PluginUtils.setCacheStorageFactory(cacheFactory);
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
