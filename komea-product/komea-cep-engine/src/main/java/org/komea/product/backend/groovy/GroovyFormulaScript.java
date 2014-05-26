/**
 * 
 */

package org.komea.product.backend.groovy;



import groovy.lang.Script;

import org.apache.commons.lang3.Validate;
import org.komea.eventory.api.engine.ICEPQuery;
import org.komea.eventory.api.engine.IDynamicDataQuery;
import org.komea.product.backend.service.ISpringService;



/**
 * @author sleroy
 */
public abstract class GroovyFormulaScript extends Script
{
    
    
    /**
     * AUtowiring the cep event query.
     * 
     * @param _query
     *            the query.
     * @return
     */
    
    public ICEPQuery autowired(final ICEPQuery _query) {
    
    
        getSpringService().autowirePojo(_query);
        return _query;
        
    }
    
    
    /**
     * Autowiring the data query.
     * 
     * @param _query
     *            the query
     * @return the dynamic data query
     */
    public IDynamicDataQuery<?> autowired(final IDynamicDataQuery _query) {
    
    
        getSpringService().autowirePojo(_query);
        return _query;
        
    }
    
    
    public ISpringService getSpringService() {
    
    
        final ISpringService variable = (ISpringService) getBinding().getVariable("spring");
        Validate.notNull(variable, "Spring service is not null");
        return variable;
    }
}
