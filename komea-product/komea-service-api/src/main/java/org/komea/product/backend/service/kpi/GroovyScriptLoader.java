
package org.komea.product.backend.service.kpi;



import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class GroovyScriptLoader
{
    
    
    private static final Logger       LOGGER     = LoggerFactory
                                                         .getLogger(GroovyScriptLoader.class);
    private final ClassLoader         classLoader;
    
    private final Map<String, String> parameters = new HashMap();
    
    private final String              resourcePath;
    
    
    
    public GroovyScriptLoader(final ClassLoader _classLoader, final String _resourcePath) {
    
    
        super();
        classLoader = _classLoader;
        resourcePath = _resourcePath;
        
    }
    
    
    public GroovyScriptLoader(final String _script) {
    
    
        this(Thread.currentThread().getContextClassLoader(), _script);
    }
    
    
    public void addParameter(final String _key, final String _value) {
    
    
        parameters.put(_key, _value);
    }
    
    
    /**
     * Loads the script
     *
     * @return the script text
     */
    public String load() {
    
    
        InputStream resourceAsStream = null;
        String script = "##notloaded##";
        try {

            resourceAsStream = classLoader.getResourceAsStream(resourcePath);
            script = IOUtils.toString(resourceAsStream);
            Validate.notNull(script);
            for (final Entry<String, String> pattern : parameters.entrySet()) {
                script = script.replace(pattern.getKey(), pattern.getValue());
            }
            
        } catch (final Exception e) {
            LOGGER.error("Impossible to retrieve Groovy script template : script {}", script, e);
            throw new RuntimeException("Could not load the script " + script);
        } finally {
            IOUtils.closeQuietly(resourceAsStream);
        }
        return script;
    }
}
