/**
 *
 */

package org.komea.product.backend.groovy;



import groovy.lang.Script;

import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.eventory.api.engine.IDynamicDataQuery;
import org.komea.eventory.query.CEPQuery;
import org.komea.product.backend.api.ISpringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;



/**
 * @author sleroy
 */
public abstract class AbstractGroovyScript extends Script
{


    private static final Logger       LOGGER      = LoggerFactory
                                                          .getLogger(AbstractGroovyScript.class);

    private final Map<String, Object> annotations = Maps.newHashMap();
    
    
    
    public AbstractGroovyScript() {


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


    public void define(final String _annotationName, final Object _value) {
    
    
        annotations.put(_annotationName, _value);
    }


    /**
     * Returns a spring service implementing the given class.
     */
    public <T> T getService(final Class<T> _class) {


        return getSpringService().getBean(_class);
    }


    public ISpringService getSpringService() {


        final ISpringService variable = (ISpringService) getBinding().getVariable("spring");
        Validate.notNull(variable, "spring service not available.");
        return variable;
    }


    /**
     * Initializes the CEP Query
     *
     * @param _queryImplementation
     *            the query implementation.
     * @return the cep query.
     */
    public CEPQuery query_from_definition(
            final Class<? extends ICEPQueryImplementation> _queryImplementation) {


        LOGGER.debug("Initialization of a cep query {}", _queryImplementation);
        final ICEPQueryImplementation queryImplementation =
                org.springframework.beans.BeanUtils.instantiate(_queryImplementation);
        return new CEPQuery(queryImplementation);
    }


    public Map<String, Object> getAnnotations() {
    
    
        return annotations;
    }


}
