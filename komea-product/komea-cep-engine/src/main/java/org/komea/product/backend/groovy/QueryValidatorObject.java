/**
 * 
 */

package org.komea.product.backend.groovy;



/**
 * @author sleroy
 */
public class QueryValidatorObject
{
    
    
    public boolean validateQuery(final String _formula) {
    
    
        final GroovyEngineService groovyEngineService = new GroovyEngineService();
        groovyEngineService.init();
        final boolean res = groovyEngineService.isValidFormula(_formula);
        groovyEngineService.destroy();
        return res;
    }
}
