/**
 * 
 */

package org.komea.product.backend.groovy;



import groovy.lang.Script;

import org.apache.commons.lang3.Validate;
import org.komea.eventory.api.engine.ICEPQuery;
import org.komea.eventory.api.engine.IDynamicDataQuery;
import org.komea.product.backend.service.ISpringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author sleroy
 */
public abstract class GroovyFormulaScript extends Script
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyFormulaScript.class);
    
    
    
    /**
     * AUtowiring the cep event query.
     * 
     * @param _query
     *            the query.
     * @return
     */
    
    public ICEPQuery autowired(final ICEPQuery _query) {
    
    
        LOGGER.info("Autowiring the cep query {}", _query);
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
    
    
        LOGGER.info("Autowiring the dynamic data query {}", _query);
        getSpringService().autowirePojo(_query);
        return _query;
        
    }
    
    
    public ISpringService getSpringService() {
    
    
        final ISpringService variable = (ISpringService) getBinding().getVariable("spring");
        Validate.notNull(variable, "Spring service must be provided to the groovy shell.");
        return variable;
    }
}
