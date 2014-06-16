/**
 *
 */

package org.komea.product.backend.groovy;



import org.komea.product.backend.api.ISpringService;
import org.komea.product.backend.api.exceptions.GroovyScriptException.GroovyValidationStatus;
import org.komea.product.backend.service.groovy.GroovyEngineService;

import static org.mockito.Mockito.mock;



/**
 * This clas is used for test only.
 *
 * @author sleroy
 */
public class QueryValidatorObject
{
    
    
    public boolean validateQuery(final String _formula) {
    
    
        final GroovyEngineService groovyEngineService = new GroovyEngineService();
        final ISpringService mock = mock(ISpringService.class);
        groovyEngineService.setSpringService(mock);
        groovyEngineService.init();
        final boolean res = groovyEngineService.isValidQuery(_formula) == GroovyValidationStatus.OK;
        groovyEngineService.destroy();
        return res;
    }
}
