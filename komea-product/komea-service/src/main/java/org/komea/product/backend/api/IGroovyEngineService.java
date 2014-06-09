
package org.komea.product.backend.api;



import groovy.lang.Binding;
import groovy.lang.Script;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.api.exceptions.GroovyScriptException.GroovyValidationStatus;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Kpi;



public interface IGroovyEngineService
{


    /**
     * Parses the formula into a query.
     *
     * @param _formula
     *            the formula
     * @return true if the formula is valid.
     */
    public GroovyValidationStatus isValidFormula(final String _formula);


    /**
     * Parses a groovy script
     *
     * @param _groovyScript
     * @return the parsed groovy script.
     */
    public <T> Class<T> parseClass(String _groovyScript);


    /**
     * Parses a query
     *
     * @param _kpi
     * @return the instantiated query
     */
    public <T extends IQuery<KpiResult>> T parseQuery(Kpi _kp);


    /**
     * Tests if a formula is a valid script
     *
     * @param _formula
     *            the formula
     * @return the validation status.
     */
    GroovyValidationStatus isValidScript(String _formula);


    /**
     * Returns a script object parsed from a source.
     *
     * @param _groovyScript
     *            the groovy script text
     * @return the script
     */
    Script parseScript(Kpi _kpi);


    /**
     * Parses a groovy script.
     *
     * @param _script
     *            the script
     * @param _config
     *            the groovy configuration
     * @param _binding
     *            the variable binding.
     * @return the script or null or an exception.
     */
    Script parseScript(String _script, CompilerConfiguration _config, Binding _binding);


    /**
     * @param _class
     */
    void registerClassImport(Class _class);


    /**
     * @param _import
     */
    void registerStarImport(String _import);

}
