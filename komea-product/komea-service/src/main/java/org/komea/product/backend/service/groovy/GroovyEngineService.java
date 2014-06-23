
package org.komea.product.backend.service.groovy;



import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.Validate;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.api.IGroovyEngineService;
import org.komea.product.backend.api.ISpringService;
import org.komea.product.backend.api.exceptions.GroovyScriptException;
import org.komea.product.backend.api.exceptions.GroovyScriptException.GroovyValidationStatus;
import org.komea.product.backend.groovy.AbstractGroovyScript;
import org.komea.product.backend.service.plugins.IQueryAnnotations;
import org.komea.product.backend.service.queries.IQueryWithAnnotations;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Kpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;



/**
 */
@Component
public class GroovyEngineService implements IGroovyEngineService
{


    /**
     * @author sleroy
     * @param <T>
     */
    private final class QueryWithAnnotations<T extends IQuery> implements IQueryWithAnnotations<T>
    {


        private final QueryAnnotations annotations;
        private final T                query;



        /**
         * @param _parseScript
         */
        private QueryWithAnnotations(final Map<String, Object> _annotations, final T _query) {


            annotations = new QueryAnnotations(_annotations);
            query = _query;

        }


        @Override
        public IQueryAnnotations getAnnotations() {


            return annotations;
        }


        @Override
        public T getQuery() {


            return query;
        }
    }



    private static final Logger LOGGER         = LoggerFactory.getLogger("groovy-service");


    private final Set<Class>    classImports   = Sets.newHashSet();


    private final Set<String>   dynamicImports = Sets.newHashSet();


    private GroovyClassLoader   groovyClassLoader;


    @Autowired
    private ISpringService      springService;



    @PreDestroy
    public void destroy() {


        LOGGER.info("Destroying Groovy engine.");
        groovyClassLoader.clearCache();
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


        /**
         * Registering imports
         */
        registerStarImport("org.komea.eventory.query");
        registerStarImport("org.komea.eventory.api.cache");
        registerStarImport("org.komea.product.database.alert");
        registerStarImport("org.komea.product.database.alert.enums");
        registerStarImport("org.komea.product.database.api");
        registerStarImport("org.komea.product.database.dto");
        registerStarImport("org.komea.product.database.enums");
        registerStarImport("org.komea.product.database.model");
        registerStarImport("org.komea.product.database.utils");
        registerStarImport("org.komea.product.model.timeserie");
        registerStarImport("org.komea.product.model.timeserie.dto");
        registerStarImport("org.komea.product.plugins.bugtracking.model");
        registerStarImport("org.komea.product.plugins.datasource");
        registerStarImport("org.komea.product.plugins.model");
        registerStarImport("org.komea.product.plugins.projectmanagement.model");
        registerStarImport("org.komea.product.plugins.repository.model");
        registerStarImport("org.komea.product.plugins.timemanagement.model");
        registerStarImport("org.komea.product.service.dto");
        registerStarImport("org.komea.product.service.dto.errors");
        registerStarImport("org.komea.product.backend.service.kpi");

    }


    @Override
    public GroovyValidationStatus isValidFormula(final String _formula) {


        return isValidQuery(_formula);
    }


    @Override
    public GroovyValidationStatus isValidGroovyScript(final String _formula) {


        final Kpi kpi = new Kpi();
        kpi.setEsperRequest(_formula);
        kpi.setId(1);
        GroovyValidationStatus status = GroovyValidationStatus.OK;
        try {
            Validate.notNull(parseScript(kpi));
        } catch (final GroovyScriptException e) {
            LOGGER.error(e.getMessage(), e);
            status = e.getStatus();
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            status = GroovyValidationStatus.UNKNOWN;
        }
        return status;
    }


    @Override
    public GroovyValidationStatus isValidQuery(final String _formula) {


        final Kpi kpi = new Kpi();
        kpi.setEsperRequest(_formula);
        kpi.setId(1);
        GroovyValidationStatus status = GroovyValidationStatus.OK;
        try {
            Validate.notNull(parseQuery(kpi));
        } catch (final GroovyScriptException e) {
            LOGGER.error(e.getMessage(), e);
            status = e.getStatus();
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            status = GroovyValidationStatus.UNKNOWN;
        }
        return status;
    }


    @Override
    public GroovyValidationStatus isValidScript(final String _formula) {


        return isValidGroovyScript(_formula);
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
            throw new GroovyScriptException(_groovyScript, GroovyValidationStatus.PARSING_FAILED, e);
        }

    }


    @SuppressWarnings("rawtypes")
    @Override
    public <T extends IQuery<KpiResult>> T parseQuery(final Kpi _kpi) {


        return (T) parseQueryAndAnnotations(_kpi).getQuery();
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.api.IGroovyEngineService#parseQueryAndAnnotations(org.komea.product.database.model.Kpi)
     */
    @Override
    public <T extends IQuery<KpiResult>> IQueryWithAnnotations<T> parseQueryAndAnnotations(
            final Kpi _kpi) {


        IQuery cast = null;
        final Script parseScript = parseScript(_kpi);
        Object resultOfScriptExecution = null;
        try {


            resultOfScriptExecution = parseScript.run();
            cast = IQuery.class.cast(resultOfScriptExecution);

            injectSpringIntoScript(cast);
            return new QueryWithAnnotations<T>(
                    ((AbstractGroovyScript) parseScript).getAnnotations(), (T) cast);
        } catch (final Exception e) {
            LOGGER.error("Exception during execution of a kpi  {}", e.getMessage(), e);
            throw new GroovyScriptException(_kpi, GroovyValidationStatus.EXECUTION_FAILED, e);
        }
        
        
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
        Validate.notNull(_kpi.getEsperRequest(), "Kpi should define a formula");
        final CompilerConfiguration config = new CompilerConfiguration();
        buildImportCustomizer(config);

        config.setScriptBaseClass(AbstractGroovyScript.class.getCanonicalName());
        final Binding binding = new Binding();
        binding.setVariable("spring", springService);
        binding.setVariable("kpiid", _kpi.getId());

        parseScript = parseScript(_kpi.getEsperRequest(), config, binding);
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


        try {
            final GroovyShell shell =
                    new GroovyShell(Thread.currentThread().getContextClassLoader(), binding, config);

            return shell.parse(_script);
        } catch (final Exception e) {
            throw new GroovyScriptException(_script, GroovyValidationStatus.PARSING_FAILED, e);
        }
    }


    @Override
    public void registerClassImport(final Class _class) {


        classImports.add(_class);

    }


    @Override
    public void registerStarImport(final String _import) {


        dynamicImports.add(_import);
    }


    public void setSpringService(final ISpringService _springService) {


        springService = _springService;
    }


    private void buildImportCustomizer(final CompilerConfiguration config) {


        final ImportCustomizer importCustomizer = new ImportCustomizer();
        for (final String dynImport : dynamicImports) {
            importCustomizer.addStarImports(dynImport);
        }
        for (final Class clazz : classImports) {
            importCustomizer.addImports(clazz.getName());
        }
        config.addCompilationCustomizers(importCustomizer);
    }


    private void injectSpringIntoScript(final IQuery cast) throws Exception {


        if (springService != null) {
            springService.autowirePojo(cast);
        } else {
            LOGGER.error("Spring injection is disabled");
        }


    }
}
