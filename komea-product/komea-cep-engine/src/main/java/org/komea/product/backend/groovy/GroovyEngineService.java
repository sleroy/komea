
package org.komea.product.backend.groovy;



import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.io.IOUtils;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.service.ISpringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 */
@Component
public class GroovyEngineService implements IGroovyEngineService
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger("groovy-service");
    private GroovyClassLoader   groovyClassLoader;
    
    
    @Autowired
    private ISpringService      springService;
    
    
    
    @PreDestroy
    public void destroy() {
    
    
        LOGGER.info("Destroying Groovy engine.");
        IOUtils.closeQuietly(groovyClassLoader);
        groovyClassLoader = null;
    }
    
    
    @PostConstruct
    public void init() {
    
    
        LOGGER.info("Initializing Groovy engine.... please wait...");
        
        final ClassLoader parent = Thread.currentThread().getContextClassLoader();
        groovyClassLoader = new GroovyClassLoader(parent);
        
    }
    
    
    @Override
    public boolean isValidFormula(final String _formula) {
    
    
        return parseScript(_formula).run() instanceof IQuery;
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.groovy.IGroovyEngineService#parseGroovyScript
     * (java.lang.String)
     */
    @Override
    public <T> Class<T> parseClass(final String _groovyScript) {
    
    
        Class<T> groovyClass;
        try {
            groovyClassLoader.setShouldRecompile(true);
            groovyClass = groovyClassLoader.parseClass(_groovyScript);
            return groovyClass;
        } catch (final Exception e) {
            LOGGER.error("Script {} presents an error {}", _groovyScript, e);
            throw new GroovyParsingException(_groovyScript, e);
        }
        
    }
    
    
    @Override
    public <T extends IQuery> T parseQuery(final String _groovyScript) {
    
    
        return (T) IQuery.class.cast(parseScript(_groovyScript).run());
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.groovy.IGroovyEngineService#parseGroovyScript
     * (java.lang.String)
     */
    @Override
    public Script parseScript(final String _groovyScript) {
    
    
        final CompilerConfiguration config = new CompilerConfiguration();
        config.setScriptBaseClass(GroovyFormulaScript.class.getCanonicalName());
        final Binding binding = new Binding();
        final GroovyShell shell =
                new GroovyShell(Thread.currentThread().getContextClassLoader(), binding, config);
        shell.setVariable("spring", springService);
        return shell.parse(_groovyScript);
        
    }
}
