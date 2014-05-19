/**
 * 
 */

package org.komea.eventory.query;



import java.io.Serializable;

import org.apache.commons.lang.Validate;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.ICEPQuery;
import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.eventory.api.engine.ICEPStatement;
import org.komea.eventory.api.filters.IFilterDefinition;
import org.komea.eventory.api.formula.ICEPFormula;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This class defines a CEP Query.
 * 
 * @author sleroy
 */
@SuppressWarnings("unchecked")
public class CEPQuery<TEvent extends Serializable, TRes> implements ICEPQuery<TEvent, TRes>
{
    
    
    private static final Logger       LOGGER       = LoggerFactory.getLogger("cep-query");
    private final BackupDelay         backupDelay;
    
    private ICEPStatement<TEvent>     cepStatement = new CEPStatement<TEvent>();
    
    private ICEPFormula<TEvent, TRes> formula;
    
    
    
    /**
     * Builds a cep query
     * 
     * @param _queryDefinition
     *            the definition of the query
     */
    public CEPQuery(final ICEPQueryImplementation _queryDefinition) {
    
    
        LOGGER.debug(">---- new cep query :");
        Validate.isTrue(new QueryDefinitionValidator().validate(_queryDefinition),
                "Query definition is not valid");
        cepStatement = new CEPStatement<TEvent>();
        LOGGER.debug(">---- filters defined : {}", _queryDefinition.getFilterDefinitions().size());
        for (final IFilterDefinition definition : _queryDefinition.getFilterDefinitions()) {
            LOGGER.debug(">---- filter choose : {}", definition);
            ((CEPStatement<TEvent>) cepStatement).addStorage(new CEPEventStorage<Serializable>(
                    definition));
        }
        
        LOGGER.debug(">---- formula defined : {}", _queryDefinition.getFormula());
        formula = (ICEPFormula) _queryDefinition.getFormula(); // Dangerous cast
                                                               // since Java and
                                                               // genericity, I
                                                               // don't know how
                                                               // to improve it
        LOGGER.debug(">---- backup delay defined : {}", _queryDefinition.getBackupDelay());
        backupDelay = _queryDefinition.getBackupDelay();
        if (cepStatement.getEventStorages().isEmpty()) {
            LOGGER.warn("Query {} should probably define at least one filter", _queryDefinition);
        }
        
    }
    
    
    @Override
    public BackupDelay getBackupDelay() {
    
    
        return backupDelay;
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
    public ICEPFormula<TEvent, TRes> getFormula() {
    
    
        return formula;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPQuery#getResult()
     */
    @Override
    public TRes getResult() {
    
    
        return formula.compute(cepStatement);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPQuery#getStatement()
     */
    @Override
    public ICEPStatement<TEvent> getStatement() {
    
    
        return getCepStatement();
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.eventory.api.ICEPQuery#notifyEvent(org.komea.product.database
     * .alert.Event)
     */
    @Override
    public void notifyEvent(final Serializable _event) {
    
    
        Validate.notNull(_event, "null event provided");
        LOGGER.debug("Query {} received event {}", _event);
        getCepStatement().notifyEvent(_event);
        
    }
    
    
    /**
     * Sets the cep statement
     * 
     * @param _cepStatement
     *            the cep statement.
     */
    public void setCepStatement(final ICEPStatement<TEvent> _cepStatement) {
    
    
        cepStatement = _cepStatement;
    }
    
    
    /**
     * @param _formula
     */
    public void setFormula(final ICEPFormula<TEvent, TRes> _formula) {
    
    
        formula = _formula;
        
    }
    
    
    @Override
    public String toString() {
    
    
        return "CEPQuery [cepStatement="
                + cepStatement + ", formula=" + formula + ", backupDelay=" + backupDelay + "]";
    }
    
}
