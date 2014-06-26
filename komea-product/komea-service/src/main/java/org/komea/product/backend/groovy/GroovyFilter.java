/**
 * 
 */

package org.komea.product.backend.groovy;



import groovy.lang.Script;

import org.komea.product.backend.utils.IFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author sleroy
 */
public class GroovyFilter<T> implements IFilter<T>
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger("groovy-filter");
    private final Script        script;
    
    private final String        variableName;
    
    
    
    /**
     * @param _value
     *            the value to evaluate
     * @param _script
     *            the groovy script defining the filter
     * @param _variableName
     *            the variable name
     */
    public GroovyFilter(final Script _script, final String _variableName) {
    
    
        super();
        
        script = _script;
        variableName = _variableName;
        
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.utils.IFilter#matches(java.lang.Object)
     */
    @Override
    public boolean matches(final T _value) {
    
    
        script.setProperty(variableName, _value);
        boolean res = false;
        try {
            res = (Boolean) script.run();
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return res;
        
    }
    
}
