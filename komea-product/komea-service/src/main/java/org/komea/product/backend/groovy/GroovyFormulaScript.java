/**
 * 
 */

package org.komea.product.backend.groovy;



import groovy.lang.Script;

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
    
    
    
    public GroovyFormulaScript() {
    
    
        super();
    }
    
    
    /**
     * AUtowiring the cep event query.
     * 
     * @param _query
     *            the query.
     * @return
     */
    public ICEPQuery autowired(final ICEPQuery _query) {
    
    
        LOGGER.debug("Autowiring the cep query {}", _query);
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
    
    
        LOGGER.debug("Autowiring the dynamic data query {}", _query);
        getSpringService().autowirePojo(_query);
        return _query;
        
    }
    
    
    public ISpringService getSpringService() {
    
    
        return (ISpringService) getBinding().getVariable("spring");
    }
    
}
