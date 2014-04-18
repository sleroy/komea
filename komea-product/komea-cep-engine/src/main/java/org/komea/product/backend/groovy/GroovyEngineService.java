
package org.komea.product.backend.groovy;



import groovy.lang.GroovyClassLoader;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.io.IOUtils;
import org.komea.eventory.utils.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



/**
 */
@Component
public class GroovyEngineService
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger("groovy-service");
    private GroovyClassLoader   groovyClassLoader;
    
    
    
    @PreDestroy
    public void destroy() {
    
    
        LOGGER.error("Destorying Groovy engine.");
        IOUtils.closeQuietly(groovyClassLoader);
        groovyClassLoader = null;
    }
    
    
    @PostConstruct
    public void init() {
    
    
        LOGGER.info("Initializing Groovy engine.... please wait...");
        
        final ClassLoader parent = getClass().getClassLoader();
        groovyClassLoader = new GroovyClassLoader(parent);
        
    }
    
    
    /**
     * Parses a groovy script
     * 
     * @param _groovyScript
     * @return the parsed groovy script.
     */
    public <T> T parseGroovyScript(final String _groovyScript) {
    
    
        Class<T> groovyClass;
        try {
            groovyClass = groovyClassLoader.parseClass(_groovyScript);
        } catch (final Exception e) {
            throw new GroovyParsingException(e);
        }
        return ClassUtils.instantiate(groovyClass);
    }
}
