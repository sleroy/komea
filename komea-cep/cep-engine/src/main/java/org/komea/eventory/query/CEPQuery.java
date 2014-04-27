/**
 * 
 */

package org.komea.eventory.query;



import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.komea.eventory.api.engine.ICEPQuery;
import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.eventory.api.engine.ICEPStatement;
import org.komea.eventory.api.filters.IFilterDefinition;
import org.komea.eventory.api.formula.ICEPFormula;
import org.komea.eventory.api.formula.ICEPResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This class defines a CEP Query.
 * 
 * @author sleroy
 */
@SuppressWarnings("unchecked")
public class CEPQuery implements ICEPQuery
{
    
    
    private static final Logger LOGGER       = LoggerFactory.getLogger("cep-query");
    private ICEPStatement<?>    cepStatement = new CEPStatement<Serializable>();
    
    
    private ICEPFormula         formula;
    private Map<String, Object> parameters   = Collections.<String, Object> emptyMap();
    
    
    
    /**
     * Builds a cep query
     * 
     * @param _queryDefinition
     *            the definition of the query
     */
    public CEPQuery(final ICEPQueryImplementation _queryDefinition) {
    
    
        LOGGER.debug(">---- new cep query :");
        
        final CEPStatement<Serializable> initStatement = new CEPStatement<Serializable>();
        LOGGER.debug(">---- filters defined : {}", _queryDefinition.getFilterDefinitions().size());
        for (final IFilterDefinition definition : _queryDefinition.getFilterDefinitions()) {
            LOGGER.debug(">---- filter choose : {}", definition);
            initStatement.add(new CEPEventStorage<Serializable>(definition));
        }
        cepStatement = initStatement;
        LOGGER.debug(">---- parameters defined : {}", _queryDefinition.getParameters());
        parameters = _queryDefinition.getParameters();
        LOGGER.debug(">---- formula defined : {}", _queryDefinition.getFormula());
        formula = _queryDefinition.getFormula();
        Validate.notNull(formula);
        Validate.notNull(parameters);
        if (cepStatement.getEventStorages().isEmpty()) {
            LOGGER.warn("Query {} should probably define at least one filter", _queryDefinition);
        }
    }
    
    
    /**
     * Returns the CEP Statement
     * 
     * @return the cep statement
     */
    public ICEPStatement getCepStatement() {
    
    
        return cepStatement;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPQuery#getFormula()
     */
    @Override
    public <T extends Serializable> ICEPFormula<T> getFormula() {
    
    
        return formula;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPQuery#getParameters()
     */
    @Override
    public Map<String, Object> getParameters() {
    
    
        return parameters;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPQuery#getResult()
     */
    @Override
    public ICEPResult getResult() {
    
    
        return formula.compute(cepStatement, parameters);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPQuery#getStatement()
     */
    @Override
    public <T extends Serializable> ICEPStatement<T> getStatement() {
    
    
        return getCepStatement();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPQuery#notifyEvent(org.komea.product.database.alert.Event)
     */
    @Override
    public void notifyEvent(final Serializable _event) {
    
    
        Validate.notNull(_event);
        LOGGER.debug("Query {} received event {}", _event);
        getCepStatement().notifyEvent(_event);
        
    }
    
    
    /**
     * Sets the cep statement
     * 
     * @param _cepStatement
     *            the cep statement.
     */
    public void setCepStatement(final ICEPStatement<?> _cepStatement) {
    
    
        cepStatement = _cepStatement;
    }
    
    
    /**
     * @param _formula
     */
    public void setFormula(final ICEPFormula<?> _formula) {
    
    
        formula = _formula;
        
        
    }
    
    
    /**
     * @param _parameters
     */
    public void setParameters(final Map<String, Object> _parameters) {
    
    
        parameters = _parameters;
        
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "CEPQuery [cepStatement="
                + cepStatement + ", formula=" + formula + ", parameters=" + parameters + "]";
    }
    
    
}
