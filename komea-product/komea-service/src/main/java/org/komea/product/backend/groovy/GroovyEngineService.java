
package org.komea.product.backend.groovy;



import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.api.ISpringService;
import org.komea.product.backend.plugin.api.RequiresSpring;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Kpi;
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
    
    
    public ISpringService getSpringService() {
    
    
        return springService;
    }
    
    
    @PostConstruct
    public void init() {
    
    
        LOGGER.info("Initializing Groovy engine.... please wait...");
        
        final ClassLoader parent = Thread.currentThread().getContextClassLoader();
        groovyClassLoader = new GroovyClassLoader(parent);
        
    }
    
    
    @Override
    public boolean isValidFormula(final String _formula) {
    
    
        final Kpi kpi = new Kpi();
        kpi.setEsperRequest(_formula);
        kpi.setId(1);
        try {
            return parseQuery(kpi) instanceof IQuery;
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
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
    
    
    @SuppressWarnings("rawtypes")
    @Override
    public <T extends IQuery<KpiResult>> T parseQuery(final Kpi _kpi) {
    
    
        IQuery cast = null;
        final Script parseScript = parseScript(_kpi);
        try {
            
            
            cast = IQuery.class.cast(parseScript.run());
            if (org.springframework.core.annotation.AnnotationUtils.isAnnotationInherited(
                    RequiresSpring.class, cast.getClass())) {
                springService.autowirePojo(cast);
            }
        } catch (final Exception e) {
            LOGGER.error("Errors occured during the EXECUTION of a groovy script : {}\n",
                    _kpi.getEsperRequest(), e);
        }
        return (T) cast;
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.groovy.IGroovyEngineService#parseGroovyScript
     * (java.lang.String)
     */
    @Override
    public Script parseScript(final Kpi _kpi) {
    
    
        Script parseScript = null;
        try {
            Validate.notNull(_kpi.getEsperRequest(), "Kpi should define a formula");
            final CompilerConfiguration config = new CompilerConfiguration();
            config.setScriptBaseClass(GroovyFormulaScript.class.getCanonicalName());
            final Binding binding = new Binding();
            binding.setVariable("spring", springService);
            binding.setVariable("kpiid", _kpi.getId());
            
            parseScript = parseScript(_kpi.getEsperRequest(), config, binding);
            
        } catch (final Exception e) {
            LOGGER.error("Errors occured during the parsing of the groovy script : \n{}",
                    _kpi.getEsperRequest(), e);
        }
        return parseScript;
    }
    
    
    /**
     * Parses a script
     * 
     * @param _script
     *            the script
     * @param config
     *            the groovy configuration
     * @param binding
     *            the binding.
     * @return the script or null or an exception
     */
    @Override
    public Script parseScript(
            final String _script,
            final CompilerConfiguration config,
            final Binding binding) {
    
    
        final GroovyShell shell =
                new GroovyShell(Thread.currentThread().getContextClassLoader(), binding, config);
        
        return shell.parse(_script);
    }
    
    
    public void setSpringService(final ISpringService _springService) {
    
    
        springService = _springService;
    }
}
