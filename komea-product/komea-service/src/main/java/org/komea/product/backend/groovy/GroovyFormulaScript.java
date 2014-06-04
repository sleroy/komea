/**
 * 
 */

package org.komea.product.backend.groovy;



import groovy.lang.Script;

import org.komea.eventory.api.cache.ICacheStorageFactory;
import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.eventory.api.engine.IDynamicDataQuery;
import org.komea.eventory.query.CEPQuery;
import org.komea.product.backend.api.ISpringService;
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
    
    
    /**
     * AUtowiring the cep event query.
     * 
     * @param _query
     *            the query.
     * @return
     */
    public <T> T autowired(final T _query) {
    
    
        LOGGER.debug("Autowiring the cep query {}", _query);
        getSpringService().autowirePojo(_query);
        return _query;
        
    }
    
    
    /**
     * Returns a spring service implementing the given class.
     */
    public <T> T getService(final Class<T> _class) {
    
    
        return getSpringService().getBean(_class);
    }
    
    
    public ISpringService getSpringService() {
    
    
        return (ISpringService) getBinding().getVariable("spring");
    }
    
    
    /**
     * Initializes the CEP Query
     * 
     * @param _queryImplementation
     *            the query implementation.
     * @return the cep query.
     */
    public CEPQuery query_from_definition(final Class _queryImplementation) {
    
    
        LOGGER.debug("Initialization of a cep query {}", _queryImplementation);
        final ICEPQueryImplementation queryImplementation =
                (ICEPQueryImplementation) org.springframework.beans.BeanUtils
                        .instantiate(_queryImplementation);
        return autowired(new CEPQuery(queryImplementation, getService(ICacheStorageFactory.class)));
    }
    
}
